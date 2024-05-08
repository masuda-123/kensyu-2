package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuestionAnswersController {
	
	
    @Autowired
    private final QuestionService service;
	
	@GetMapping("/list")
	public String getList(Model model) {
		ArrayList<Question> queList = service.getQuestion();
		model.addAttribute("queList", queList);
		return "list";
	}

}
