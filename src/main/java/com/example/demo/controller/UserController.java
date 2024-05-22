package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class UserController {
	
	//以下のクラスをインスタンス化
	@Autowired
	private final UserService userService;
	
	//ログイン画面の処理
	@GetMapping("/login")
	public String getLogin() {
		//login画面に遷移
		return "login";
	}
	
	//ログアウトの処理
	@PostMapping("/logout")
	public String getLogout() {
		//login画面に遷移
		return "login";
	}
	
	//一覧画面の処理
	@GetMapping("/user/lists")
	public String getList(Model model) {
		//全てのユーザーを取得
		ArrayList<User> userList = userService.findAll();
		//変数をモデルに登録
		model.addAttribute("userList", userList);
		return "user_lists";
	}
	
	//登録画面の処理
	@GetMapping("/user/register")
	public String getRegister(Model model) {
		//register画面に遷移
		return "user_register";
	}
}
