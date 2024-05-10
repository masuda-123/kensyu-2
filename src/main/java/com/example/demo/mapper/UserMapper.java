package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	//userIdをもとにユーザーを取得
	User findById(int userId);
}
