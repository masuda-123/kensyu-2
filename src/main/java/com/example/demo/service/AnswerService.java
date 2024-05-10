package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AnswerMapper;
import com.example.demo.model.Answer;

@Service
public class AnswerService {
	
	//以下のクラスをインスタンス化
	@Autowired
	private AnswerMapper answerMapper;
	
	//答えを全件取得
	@Transactional
	public ArrayList<Answer> getAnswer() {
		return answerMapper.getAnswer();
	}
	
	//questionIdをもとに答えを取得
	@Transactional
	public ArrayList<Answer> findById(int questionId) {
		return answerMapper.findById(questionId);
	}
	
	//答えを登録
	@Transactional
	public void registerAnswers(String[] answers, int questionId) {
		//答えを一つ一つ登録
		for(String answer : answers) {
			answerMapper.registerAnswer(answer, questionId);
		}
	}
}
