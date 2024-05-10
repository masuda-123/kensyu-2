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
}
