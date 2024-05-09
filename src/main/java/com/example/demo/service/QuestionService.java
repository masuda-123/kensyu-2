package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;

@Service
@Transactional
public class QuestionService {
    
    @Autowired
    private QuestionMapper questionMapper;

    public ArrayList<Question> getQuestion() {
        return questionMapper.getQuestion();
    }

}