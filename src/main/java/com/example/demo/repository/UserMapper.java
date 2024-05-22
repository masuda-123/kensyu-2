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
}
