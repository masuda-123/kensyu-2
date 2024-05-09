package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
	public User findById(int userId) {
        return userMapper.findById(userId);
    }

}
