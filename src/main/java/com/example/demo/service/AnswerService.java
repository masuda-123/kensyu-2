package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AnswerMapper;
import com.example.demo.model.Answer;

@Service
@Transactional
public class AnswerService {
    
    @Autowired
    private AnswerMapper answerMapper;

    public ArrayList<Answer> getAnswer() {
        return answerMapper.getAnswer();
    }
    
    public ArrayList<Answer> findById(int questionId) {
        return answerMapper.findById(questionId);
    }

}
