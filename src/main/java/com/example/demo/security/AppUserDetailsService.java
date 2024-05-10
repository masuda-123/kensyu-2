package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService userService;
	
	@Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		User user = null;
		try {
			//String型のuserIdをint型に変換
			int id = Integer.parseInt(userId);
			//ユーザーidをもとに、DBからユーザーを探し、結果をuserに格納
			user = userService.findById(id);
		//int型に変換できない場合は、userに値を格納しない
		} catch(NumberFormatException nfex) {
			
		} finally {
			//ユーザーが存在しない場合は、例外をスロー
			if (user == null) {
				throw new UsernameNotFoundException("User" + userId + "was not found in the database");
			}
		}
			
		//SpringBootのログインユーザー情報に、値をセットしてビルドしたものを返す
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getName())
				.password(user.getPassword())
				.build();
	}

}
