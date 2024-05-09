package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
		ArrayList<Answer> ansList = ansService.getAnswer();
		model.addAttribute("ansList", ansList);
		return "list";
	}

}
