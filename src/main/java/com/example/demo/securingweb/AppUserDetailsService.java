package com.example.demo.securingweb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService userService;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		//主キーであるユーザー名の文字列をキーにして、
		//データベースからユーザー情報をとってきます。
		//ユーザーが存在しない時は例外投げます。
		User user = userService.findByName(username);
		
        if (user == null) {
            throw new UsernameNotFoundException("User" + username + "was not found in the database");
        }
        
        //権限のリスト
        //AdminやUserなどが存在するが、今回は利用しないのでUSERのみを仮で設定
        //権限を利用する場合は、DB上で権限テーブル、ユーザ権限テーブルを作成し管理が必要
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        grantList.add(authority);

        //rawDataのパスワードは渡すことができないので、暗号化
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .build();
    }

}
