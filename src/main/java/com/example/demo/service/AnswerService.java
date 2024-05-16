package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Answer;
import com.example.demo.repository.AnswerMapper;

@Service
public class AnswerService {
	
	//以下のクラスをインスタンス化
	@Autowired
	private AnswerMapper answerMapper;
	
	//答えを全件取得
	@Transactional
	public ArrayList<Answer> findAll() {
		return answerMapper.findAll();
	}
	
	//questionIdをもとに答えを取得
	@Transactional
	public ArrayList<Answer> findByQuestionId(int questionId) {
		return answerMapper.findByQuestionId(questionId);
	}
	
	//答えを登録
	@Transactional
	public void register(String answer, int questionId) {
		answerMapper.register(answer, questionId);
	}
	
	//questionIdをもとに答えを削除
	@Transactional
	public void deleteByQuestionId(int questionId) {
		answerMapper.deleteByQuestionId(questionId);
	}
	
	//idをもとに答えを更新
	@Transactional
	public void update(String answer, int answerId) {
		answerMapper.update(answer, answerId);
	}
	
	//idをもとに答えを削除
	@Transactional
	public void deleteById(int answerId) {
		answerMapper.deleteById(answerId);
	}
	
	//答えを全件取得
	@Transactional
	public ArrayList<Answer> findByAnswer(String answer) {
		return answerMapper.findByAnswer(answer);
	}
}
