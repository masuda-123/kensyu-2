package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.model.Model;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String get() {
		return "login";
	}
	
	@PostMapping("/login")
	public String post(@RequestParam("userId")String userId, Model model) {
		return "login";
	}
}
