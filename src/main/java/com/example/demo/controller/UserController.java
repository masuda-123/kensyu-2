package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    /**
     * ログイン成功時に呼び出されるメソッド
     * SecurityContextHolderから認証済みユーザの情報を取得しモデルへ追加する
     * @param model リクエストスコープ上にオブジェクトを載せるためのmap
     * @return helloページのViewName
     */
    @RequestMapping("/top")
    private String init(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Principalからログインユーザの情報を取得
        String userName = auth.getName();
        model.addAttribute("userName", userName);
        return "top";

    }
}
