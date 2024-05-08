package com.example.demo.service;

import java.util.ArrayList;

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

    public ArrayList<User> getUser() {
        return userMapper.getUser();
    }
    
    public User findByName(String username) {
    	return userMapper.findByName(username);
    }

}
