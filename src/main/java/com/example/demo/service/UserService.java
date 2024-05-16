package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserMapper;

@Service
public class UserService {
	
	//以下のクラスをインスタンス化
	@Autowired
	private UserMapper userMapper;
	
	//userIdをもとにユーザーを取得
	@Transactional
	public User findById(int userId) {
		return userMapper.findById(userId);
	}
}
