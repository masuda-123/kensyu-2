package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;

@Service
public class QuestionService {
	
	//以下のクラスをインスタンス化
	@Autowired
	private QuestionMapper questionMapper;
	
	//問題を全件取得
	@Transactional
	public ArrayList<Question> findAll() {
		return questionMapper.findAll();
	}
	
	//問題を登録し、idを取得
	@Transactional
	public int register(String question) {
		//問題を登録
		questionMapper.register(question);
		//idの最大値を取得
		int questionId = questionMapper.findMaxId();
		return questionId;
	}
	
	//idから問題を取得
	@Transactional
	public Question findById(int questionId) {
		return questionMapper.findById(questionId);
	}
	
	//idから問題を削除
	@Transactional
	public void delete(int questionId) {
		questionMapper.delete(questionId);
	}
}
