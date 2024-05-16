package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionMapper;

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
	
	//idをもとに問題を取得
	@Transactional
	public Question findById(int questionId) {
		return questionMapper.findById(questionId);
	}
	
	//idをもとに問題を削除
	@Transactional
	public void delete(int questionId) {
		questionMapper.delete(questionId);
	}
	
	//idをもとに問題を更新
	@Transactional
	public void update(int questionId, String question) {
		questionMapper.update(questionId, question);
	}
	
	//問題をランダムに取得
	@Transactional
	public ArrayList<Question> findAllOrderByRand(){
		return questionMapper.findAllOrderByRand();
	}
}
