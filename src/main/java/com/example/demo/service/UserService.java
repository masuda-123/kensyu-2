package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserMapper;
import com.example.demo.security.PasswordEncrypter;

@Service
public class UserService implements UserDetailsService {
	
	//以下のクラスをインスタンス化
	@Autowired
	private UserMapper userMapper;
	
	//UserDetailsServiceのメソッドをオーバーライドし、ログイン時の認証方法を設定
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		User user = null;
		try {
			//String型のuserIdをint型に変換
			int id = Integer.parseInt(userId);
			//ユーザーidをもとに、DBからユーザーを探し、結果をuserに格納
			user = userMapper.findById(id);
		//int型に変換できない場合は、userに値を格納しない
		} catch(NumberFormatException nfex) {
			
		} finally {
			//ユーザーが存在しない場合は、例外をスロー
			if (user == null) {
				throw new UsernameNotFoundException("User_id = " + userId + " was not found in the database");
			}
		}
		return user;
	}
	
	//useIdをもとにユーザーを取得
	@Transactional
	public User findById(int userId) {
		return userMapper.findById(userId);
	}
	
	//ユーザーを全て取得
	@Transactional
	public ArrayList<User> findAll() {
		return userMapper.findAll();
	}
	
	//ユーザーを登録
	@Transactional
	public void register(String userName, String password, int adminFlag) {
		//パスワードは暗号化
		PasswordEncrypter passEncrypter = new PasswordEncrypter();
		userMapper.register(userName, passEncrypter.encode(password), adminFlag);
	}
	
	//userIdをもとにユーザーを更新
	@Transactional
	public void update(int userId, String password, int adminFlag) {
		//パスワードは暗号化
		PasswordEncrypter passEncrypter = new PasswordEncrypter();
		userMapper.update(userId, passEncrypter.encode(password), adminFlag);
	}
	
	//userIdをもとにユーザーを削除
	@Transactional
	public void delete(int userId) {
		userMapper.delete(userId);
	}
}
