package com.example.demo.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	//userIdをもとにユーザーを取得
	User findById(int userId);
	
	//ユーザーをすべて取得
	ArrayList<User> findAll();
	
	//ユーザーを登録
	void register(String userName, String password, int adminFlag);
	
	//userIdをもとにユーザーを更新
	void update(int userId, String password, int adminFlag);
}
