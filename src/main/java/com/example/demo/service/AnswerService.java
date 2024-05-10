package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AnswerMapper;
import com.example.demo.model.Answer;
import com.example.demo.repository.AnswerRepository;

@Service
@Transactional
public class AnswerService {
    
    @Autowired
    private AnswerMapper answerMapper;
    
    private final AnswerRepository answerRepository;
    
    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public ArrayList<Answer> getAnswer() {
        return answerMapper.getAnswer();
    }
    
    public ArrayList<Answer> findById(int questionId) {
        return answerMapper.findById(questionId);
    }
    
    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

}
