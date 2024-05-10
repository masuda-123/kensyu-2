package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.service.AnswerService;
import com.example.demo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuestionAnswersController {
	
	
    @Autowired
    private final QuestionService queService;
    
    @Autowired
    private final AnswerService ansService;
	
	@GetMapping("/list")
	public String getList(Model model) {
		ArrayList<Question> queList = queService.getQuestion();
		model.addAttribute("queList", queList);
		ArrayList<ArrayList<Answer>> ansList = new ArrayList<>();
		for(Question que: queList) {
			ArrayList<Answer> ans = ansService.findById(que.getId());
			ansList.add(ans);
		}
		model.addAttribute("ansList", ansList);
		return "list";
	}
	
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
    @PostMapping("/confirm")
    public String postConfirm(@RequestParam("question") String question, @RequestParam("answer") String[] answers, Model model) {
    	model.addAttribute("question", question);
    	model.addAttribute("answers", answers);
        return "confirm";
    }
    
    @PostMapping("/complete")
    public String postComplete(@RequestParam("question") String question, @RequestParam("answer") String[] answers, Model model) {
    	int questionId = queService.registerQuestion(question);
    	ansService.registerAnswers(answers, questionId);
        return "redirect:/list";
    }

}
