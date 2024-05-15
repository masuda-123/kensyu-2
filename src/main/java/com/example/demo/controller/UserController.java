package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class UserController {
	
	@GetMapping("/login")
	public String getLogin() {
		//login画面に遷移
		return "login";
	}
	
	@PostMapping("/logout")
	public String getLogout() {
		//login画面に遷移
		return "login";
	}
}
