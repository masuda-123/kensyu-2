package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	//以下のクラスをインスタンス化
	@Autowired
	private final UserService userService;
	
	//Top画面の処理
	@RequestMapping("/top")
	private String top(Model model) {
		
		//sessionからuserIdを取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int userId = Integer.parseInt(auth.getName());
		//userIdをもとにuserデータを取得
		User user = userService.findById(userId);
		//変数をモデルに登録
		model.addAttribute("user", user);
		//top画面に遷移
		return "top";
	}
}
