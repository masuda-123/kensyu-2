package com.example.demo.securingweb;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		//ユーザー名をもとに、DBからユーザーを探し、結果をuserに格納
		User user = userService.findByName(username);
		
		//ユーザーが存在しない場合は、例外をスロー
		if (user == null) {
			throw new UsernameNotFoundException("User" + username + "was not found in the database");
		}
		
		//SpringBootのログインユーザー情報に、値をセットしてビルドしたものを返す
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getName())
				.password(user.getPassword())
				.build();
		}

}
