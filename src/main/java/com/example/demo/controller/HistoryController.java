package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.History;
import com.example.demo.model.User;
import com.example.demo.service.HistoryService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HistoryController {
	//以下のクラスをインスタンス化
	@Autowired
	private final HistoryService hisService;
	
	@Autowired
	private final UserService userService;
	
	//履歴画面の処理
	@GetMapping("/history")
	public String getHistory(Model model) {
		
		//sessionからuserIdを取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int userId = Integer.parseInt(auth.getName());
		//userIdをもとにuserデータを取得
		User user = userService.findById(userId);
		//userデータからuserNameを取得
		String userName = user.getName();
		
		//現在ログインしているユーザーの履歴データを、採点時間の昇順で取得
		ArrayList<History> hisList = hisService.findByUserId(userId);
		
		//履歴データから作成日を取り出し、配列に格納
		String[] dateTime = new String[hisList.size()];
		for(int i = 0; i < dateTime.length; i++) {
			// SimpleDateFormatオブジェクトを作成し、フォーマットを設定
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			dateTime[i] = sdf.format(hisList.get(i).getCreated_at());
		}
		
		//変数をモデルに登録
		model.addAttribute("hisList", hisList);
		model.addAttribute("dateTime", dateTime);
		model.addAttribute("userName", userName);
		
		//hisotry画面に遷移
		return "history";
	}

}
