package com.example.demo.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
@Mapper
public interface UserMapper {
	ArrayList<User> getUser();
	
	User findByName(String username);
}
