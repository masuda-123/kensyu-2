package com.example.demo.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.other.Validation;
import com.example.demo.service.AnswerService;
import com.example.demo.service.HistoryService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;

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
    
    @Autowired
    private final UserService userService;
    
    @Autowired
    private final HistoryService hisService;
	
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
		
		if(queList.isEmpty()) { //登録されている問題がなかった場合
			String question = "";
			String[] answers = new String[0];
			//変数をモデルに登録
			model.addAttribute("question", question);
			model.addAttribute("answers", answers);
			//register画面に遷移
			return "register";
		} else { //登録されている問題があった場合
			//list画面に遷移
			return "list";
		}
	}
	
	@GetMapping("/register")
	public String getRegister(Model model) {
		String question = "";
		String[] answers = new String[0];
		//変数をモデルに登録
		model.addAttribute("question", question);
		model.addAttribute("answers", answers);
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
	
	@PostMapping("/register")
	public String postRegister(@RequestParam("question") String question, @RequestParam("answer") String[] answers, Model model) {
		//変数をモデルに登録
		model.addAttribute("question", question);
		model.addAttribute("answers", answers);
		//register画面に遷移
		return "register";
	}
	
	@PostMapping("/register/complete")
	public String postRegisterComplete(@RequestParam("question") String question, @RequestParam("answer") String[] answers, Model model) {
		//フォームから渡された問題を登録し、questionIdを取得
		int questionId = queService.register(question);
		//questionIdをもとに、フォームから渡された答えを登録
		for(String answer : answers) {
			ansService.register(answer, questionId);
		}
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
		ansService.deleteByQuestionId(questionId);
		//list画面にリダイレクト
		return "redirect:/list";
	}
	
	@GetMapping("/edit/{id}")
	public String getEdit(@PathVariable("id") int questionId,  Model model) {
		//パスから取得した問題Idをもとに、問題と答えを取得
		Question que = queService.findById(questionId);
		ArrayList<Answer> ansList = ansService.findByQuestionId(questionId);
		//ansListからanswer、idを取得し、それぞれ配列に格納
		String[] answers = new String[ansList.size()];
		int[] answersId = new int[ansList.size()];
		for(int i = 0; i < ansList.size(); i++) {
			answers[i] = ansList.get(i).getAnswer();
			answersId[i] = ansList.get(i).getId();
		}
		//変数をモデルに登録
		model.addAttribute("questionId", questionId);
		model.addAttribute("question", que.getQuestion());
		model.addAttribute("answers", answers);
		model.addAttribute("answersId", answersId);
		//edit画面に遷移
		return "edit";
	}
	
	@PostMapping("/edit/confirm")
	public String postEditConfirm(@RequestParam("question") String question, @RequestParam("answer") String[] answers, @RequestParam("questionId") 
		int questionId, @RequestParam("answerId") int[] answersId, Model model) {
		
		//Validationクラスで、入力された問題文や答えの文字数などをチェックし、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//変数をモデルに登録
		model.addAttribute("questionId", questionId);
		model.addAttribute("question", question);
		model.addAttribute("answers", answers);
		model.addAttribute("answersId", answersId);
		model.addAttribute("errorMessage", errorMessage);
		//edit_confirm画面に遷移
		return "edit_confirm";
	}
	
	@PostMapping("/edit/{id}")
	public String postEdit(@RequestParam("question") String question, @RequestParam("answer") String[] answers, @RequestParam("questionId") 
	int questionId, @RequestParam("answerId") int[] answersId, Model model) {
		
		//変数をモデルに登録
		model.addAttribute("questionId", questionId);
		model.addAttribute("question", question);
		model.addAttribute("answers", answers);
		model.addAttribute("answersId", answersId);
		//edit画面に遷移
		return "edit";
	}
	
	@PostMapping("/edit/complete")
	public String postEditComplete(@RequestParam("question") String question, @RequestParam("answer") String[] answers, @RequestParam("questionId") 
		int questionId, @RequestParam("answerId") int[] answersId, Model model) {
		
		//フォームから渡された問題idをもとに、問題を更新
		queService.update(questionId, question);
		//フォームから渡された問題idと一致するレコードを取得し、既存の答えのデータを取得
		ArrayList<Answer> ansList = ansService.findByQuestionId(questionId);
		
		//フォームから渡された答えの数だけ処理を繰り返す
		for(int i = 0; i < answers.length; i++) {
			if( i < answersId.length) { //フォームから渡された答えの中に、idを持つものがあった場合（更新された答えがあった場合）
				ansService.update(answers[i], answersId[i]); //答えを更新
			} else { //idを持たない答えがあった場合（新たに追加された答えがあった場合）
				ansService.register(answers[i], questionId); //答えを登録
			}
		}
		//既存の答えの数の方が、フォームから渡されたidの数より多かった場合（削除された答えがあった場合）
		if(ansList.size() > answersId.length) {
			for(Answer ans : ansList) { //既存の答えの数だけ、処理を繰り返す
				if(!(Arrays.stream(answersId).anyMatch(x -> x == ans.getId()))){ //既存の答えにしかないidがあった場合
					ansService.deleteById(ans.getId()); //答えを削除
				}
			}
		}
		//list画面にリダイレクト
		return "redirect:/list";
	}
	
	@GetMapping("/test")
	public String getTest(Model model) {
		//全ての問題データを取得
		ArrayList<Question> queList = queService.findAllOrderByRand();
		//変数をモデルに登録
		model.addAttribute("queList", queList);
		//test画面に遷移
		return "test";
	}
	
	@PostMapping("/test/result")
	public String postResult(@RequestParam("questionId") int[] questionsId, @RequestParam("answer") String[] answers, Model model) {
		int correctQueCnt = 0;
		
		//questionsIdの要素数分だけ処理を繰り返す
		for(int i = 0; i < questionsId.length; i++) {
			//search_answerメソッドを呼び出して、answerと一致するレコードを取得
			ArrayList<Answer> ansList = ansService.findByAnswer(answers[i]);
			//ansListの要素数分だけ繰り返す
			for(Answer ans : ansList ) {
				//入力された答えと一致するレコードがあり、答えに紐づく問題idが一致する場合
				if(ans.getId() != 0 && ans.getQuestions_id() == questionsId[i]) {
					//正解の問題数をカウントアップ
					correctQueCnt ++;
					//繰り返し処理を抜ける
					break;
				}
			}
		}
		
		//点数を計算
		int point = Math.round(100 * correctQueCnt / questionsId.length);
		
		//sessionからuserIdを取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int userId = Integer.parseInt(auth.getName());
		//userIdをもとにuserデータを取得
		User user= userService.findById(userId);
		//userデータからuserNameを取得
		String userName = user.getName();
		
		//現在の日時をtimestampに格納
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		//日時のフィーマットを指定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String strTimestamp = sdf.format(timestamp);
		
		//履歴を登録
		
		//変数をモデルに登録
		model.addAttribute("correctQueCnt", correctQueCnt);
		model.addAttribute("queCnt", questionsId.length);
		model.addAttribute("point", point);
		model.addAttribute("userName", userName);
		model.addAttribute("dateTime",strTimestamp);
		
		//result画面に遷移
		return "result";
	}
}
