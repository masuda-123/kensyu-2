package com.example.demo.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Answer;

@Mapper
public interface AnswerMapper {
	//全ての答えを取得
	ArrayList<Answer> findAll();
	
	//questionIdをもとに答えを取得
	ArrayList<Answer> findByQuestionId(int questionId);
	
	//答えを登録
	void register(String answer, int questionId);
	
	//questionIdをもとに答えを削除
	void deleteByQuestionId(int questionId);
	
	//idをもとに答えを更新
	void update(String answer, int answerId);
	
	//idをもとに答えを削除
	void deleteById(int answerId);
}
