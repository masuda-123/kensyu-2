package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.security.PasswordEncrypter;
import com.example.demo.service.HistoryService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class UserController {
	
	//以下のクラスをインスタンス化
	@Autowired
	private final UserService userService;
	
	@Autowired
	private final HistoryService historyService;
	
	//ログイン画面の処理
	@GetMapping("/login")
	public String getLogin() {
		//login画面に遷移
		return "login";
	}
	
	//ログアウトの処理
	@PostMapping("/logout")
	public String postLogout() {
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
		//モデルに変数を登録
		model.addAttribute("userName", "");
		model.addAttribute("password", "");
		model.addAttribute("adminFlag", 0);
		//user_register画面に遷移
		return "user_register";
	}
	
	//ユーザー登録確認画面の処理
	@PostMapping("/user/register/confirm")
	public String postRegisterConfirm(@RequestParam("userName") String userName, @RequestParam("password") String password, 
			@RequestParam("passwordConfirm") String passwordConfirm, @RequestParam("adminFlag") int adminFlag, Model model) {
		//変数をモデルに登録
		model.addAttribute("userName", userName);
		model.addAttribute("password", password);
		model.addAttribute("passwordConfirm", passwordConfirm);
		model.addAttribute("adminFlag", adminFlag);
		//uer_register_confirm画面に遷移
		return "user_register_confirm";
	}
	
	//ユーザー登録確認画面から登録画面に戻った際の処理
	@PostMapping("user/register")
	public String postregister(@RequestParam("userName") String userName, @RequestParam("password") String password, 
			@RequestParam("adminFlag") int adminFlag, Model model) {
		//変数をモデルに登録
		model.addAttribute("userName", userName);
		model.addAttribute("password", password);
		model.addAttribute("adminFlag", adminFlag);
		//user_register画面に遷移
		return "user_register";
	}
	
	//ユーザー登録処理
	@PostMapping("/user/register/complete")
	public String postRegisterComplete(@RequestParam("userName") String userName, @RequestParam("password") String password, 
			@RequestParam("adminFlag") int adminFlag, Model model) {
		//フォームから渡された値をもとに、ユーザーを登録
		userService.register(userName, password, adminFlag);
		//user_lists画面にリダイレクト
		return "redirect:/user/lists";
	}
	
	//ユーザー編集画面の処理
	@GetMapping("/user/edit/{id}")
	public String getEdit(@PathVariable("id") int userId,  Model model) {
		//パスから取得したIdをもとに、ユーザーを取得
		User user = userService.findById(userId);
		PasswordEncrypter passEncrypter = new PasswordEncrypter();
		//変数をモデルに登録（パスワードは復号化する）
		model.addAttribute("userId", userId);
		model.addAttribute("userName", user.getName());
		model.addAttribute("password", passEncrypter.decrypt(user.getPassword()));
		model.addAttribute("adminFlag", user.getAdmin_flag());
		//user_edit画面に遷移
		return "user_edit";
	}
	
	//ユーザー編集確認画面の処理
	@PostMapping("/user/edit/{id}/confirm")
	public String postEditConfirm(@PathVariable("id") int userId, @RequestParam("userName") String userName, @RequestParam("password") String password, 
			@RequestParam("passwordConfirm") String passwordConfirm, @RequestParam("adminFlag") int adminFlag, Model model) {
		//変数をモデルに登録
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("password", password);
		model.addAttribute("passwordConfirm", passwordConfirm);
		model.addAttribute("adminFlag", adminFlag);
		//uer_edit_confirm画面に遷移
		return "user_edit_confirm";
	}
	
	//ユーザー編集確認画面から編集画面に戻った際の処理
	@PostMapping("user/edit/{id}")
	public String postEdit(@PathVariable("id") int userId, @RequestParam("userName") String userName, @RequestParam("password") String password, 
			@RequestParam("adminFlag") int adminFlag, Model model) {
		//変数をモデルに登録
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("password", password);
		model.addAttribute("adminFlag", adminFlag);
		//user_edit画面に遷移
		return "user_edit";
	}
	
	//ユーザー編集処理
	@PostMapping("/user/edit/{id}/complete")
	public String postEditComplete(@PathVariable("id") int userId, @RequestParam("password") String password, 
			@RequestParam("adminFlag") int adminFlag, Model model) {
		//URLのパスやフォームから渡された値をもとに、ユーザーを更新
		userService.update(userId, password, adminFlag);
		//user_lists画面にリダイレクト
		return "redirect:/user/lists";
	}
	
	//ユーザー削除確認画面の処理
	@GetMapping("/user/delete/{id}/confirm")
	public String getDeleteConfirm(@PathVariable("id") int userId, Model model) {
		//パスから取得したIdをもとに、ユーザーを取得
		User user = userService.findById(userId);
		//変数をモデルに登録（パスワードは復号化する）
		model.addAttribute("userId", userId);
		model.addAttribute("userName", user.getName());
		model.addAttribute("adminFlag", user.getAdmin_flag());
		//user_delete_confirm画面に遷移
		return "user_delete_confirm";
	}
	
	//ユーザー削除処理
	@PostMapping("/user/delete/{id}/complete")
	public String postDeleteComplete(Authentication authentication, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int userId, Model model) {
		//パスから取得したIdをもとに、ユーザーを論理削除
		userService.delete(userId);
		//idをもとに履歴も論理削除
		historyService.delete(userId);
		//sessionからuserIdを取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int currentUserId = Integer.parseInt(auth.getName());
		//現在ログインしているユーザーのidと削除したidが一致していた場合
		if(userId == currentUserId) {
			//セッションを削除
			SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
			logoutHandler.logout(request, response, authentication);
			//user_lists画面にリダイレクト
			return "redirect:/login";
		}else {
			//user_lists画面にリダイレクト
			return "redirect:/user/lists";
		}
	}
}
