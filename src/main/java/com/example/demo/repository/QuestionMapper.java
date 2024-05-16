package com.example.demo.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Question;

@Mapper
public interface QuestionMapper {
	//全ての問題を取得
	ArrayList<Question> findAll();
	
	//問題を登録
	void register(String question);
	
	//idの最大値を取得
	int findMaxId();
	
	//idから問題を取得
	Question findById(int questionId);
	
	//idから問題を削除
	void delete(int questionId);
	
	//idをもとに問題を更新
	void update(int questionId, String question);
	
	//問題をランダムに取得
	ArrayList<Question> findAllOrderByRand();
}
