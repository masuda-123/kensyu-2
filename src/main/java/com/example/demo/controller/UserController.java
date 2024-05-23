package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	//ユーザー一覧画面の処理
	@GetMapping("/user/lists")
	public String getList(Model model) {
		//全てのユーザーを取得
		ArrayList<User> userList = userService.findAll();
		//変数をモデルに登録
		model.addAttribute("userList", userList);
		return "user_lists";
	}
	
	//ユーザー登録画面の処理
	@GetMapping("/user/register")
	public String getRegister(Model model) {
		//register画面に遷移
		return "user_register";
	}
	
	//ユーザー登録確認画面の処理
	@PostMapping("/user/register/confirm")
	public String postRegisterConfirm(@RequestParam("userName") String userName, @RequestParam("password") String password, 
			@RequestParam("passwordConfirm") String passwordConfirm, @RequestParam("adminFlag") int adminFlag, Model model) {
		String userAuth = null;
		//adminFlagが1だった場合
		if(adminFlag == 1) {
			userAuth = "あり";
		//それ以外の場合
		}else {
			userAuth = "なし";
		}
		//変数をモデルに登録
		model.addAttribute("userName", userName);
		model.addAttribute("password", password);
		model.addAttribute("passwordConfirm", passwordConfirm);
		model.addAttribute("userAuth", userAuth);
		//uer_register_confirm画面に遷移
		return "user_register_confirm";
	}
	
	//ユーザー登録処理
	@PostMapping("user/register/complete")
	public String postRegisterComplete(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("userAuth") String userAuth, Model model) {
		int adminFlag = 0;
		//userAuthが「あり」だった場合
		if(userAuth.equals("あり")) {
			adminFlag = 1;
		//それ以外の場合
		}else {
			adminFlag = 0;
		}
		//フォームから渡された値をもとに、ユーザーを登録
		userService.register(userName, password, adminFlag);
		//user_lists画面にリダイレクト
		return "redirect:/user/lists";
	}
}
