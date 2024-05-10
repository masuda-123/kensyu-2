package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {
    
    @Autowired
    private QuestionMapper questionMapper;
    
    private final QuestionRepository questionRepository;
    
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public ArrayList<Question> getQuestion() {
        return questionMapper.getQuestion();
    }
    
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

}