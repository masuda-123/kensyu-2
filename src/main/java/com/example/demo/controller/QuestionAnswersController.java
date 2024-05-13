package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.other.Validation;
import com.example.demo.service.AnswerService;
import com.example.demo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuestionAnswersController {
	
	//以下のクラスをインスタンス化
    @Autowired
    private final QuestionService queService;
    
    @Autowired
    private final AnswerService ansService;
    
    @Autowired
    private final Validation val;
	
	@GetMapping("/list")
	public String getList(Model model) {
		//全ての問題データを取得
		ArrayList<Question> queList = queService.findAll();
		//全ての答えデータを取得
		ArrayList<ArrayList<Answer>> ansList = new ArrayList<>();
		//それぞれの問題に紐づく答えデータをリスト化する
		for(Question que: queList) {
			ArrayList<Answer> ans = ansService.findByQuestionId(que.getId());
			ansList.add(ans);
		}
		//変数をモデルに登録
		model.addAttribute("queList", queList);
		model.addAttribute("ansList", ansList);
		//list画面に遷移
		return "list";
	}
	
	@GetMapping("/register")
	public String getRegister() {
		//register画面に遷移
		return "register";
	}
	
	@PostMapping("/register/confirm")
	public String postRegisterConfirm(@RequestParam("question") String question, @RequestParam("answer") String[] answers, Model model) {
		//Validationクラスで、入力された問題文や答えの文字数などをチェックし、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//変数をモデルに登録
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("question", question);
		model.addAttribute("answers", answers);
		//confirm画面に遷移
		return "confirm";
	}
	
	@PostMapping("/register/complete")
	public String postRegisterComplete(@RequestParam("question") String question, @RequestParam("answer") String[] answers, Model model) {
		//フォームから渡された問題を登録し、questionIdを取得
		int questionId = queService.register(question);
		//questionIdをもとに、フォームから渡された答えを登録
		ansService.register(answers, questionId);
		//list画面にリダイレクト
		return "redirect:/list";
	}
	
	@GetMapping("/delete_confirm/{id}")
	public String getDeleteConfirm(@PathVariable("id") int questionId,  Model model) {
		//パスから取得した問題Idをもとに、問題と答えを取得
		Question que = queService.findById(questionId);
		ArrayList<Answer> ansList = ansService.findByQuestionId(questionId);
		//ansListからanswerを取得し、配列に格納
		String[] answers = new String[ansList.size()];
		for(int i = 0; i < ansList.size(); i++) {
			answers[i] = ansList.get(i).getAnswer();
		}
		//変数をモデルに登録
		model.addAttribute("questionId", que.getId());
		model.addAttribute("question", que.getQuestion());
		model.addAttribute("answers", answers);
		//delete_confirm画面に遷移
		return "delete_confirm";
	}
	
	@PostMapping("/delete/complete")
	public String postDeleteComplete(@RequestParam("questionId") int questionId, Model model) {
		//フォームから渡された問題idをもとに、問題と答えを削除
		queService.delete(questionId);
		ansService.delete(questionId);
		//list画面にリダイレクト
		return "redirect:/list";
	}
}
