package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	//Top画面の処理
	@RequestMapping("/top")
	private String top() {
		//top画面に遷移
		return "top";
	}
}
