package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class UserController {
	
    @Autowired
    private final UserService service;
    
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@PostMapping("/logout")
	public String getLogout() {
		return "login";
	}
}
