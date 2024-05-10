package com.example.demo.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Answer;

@Mapper
public interface AnswerMapper {
	//全ての答えを取得
	ArrayList<Answer> getAnswer();
	
	//questionIdをもとに答えを取得
	ArrayList<Answer> findById(int questionId);
	
	//答えを登録
	void registerAnswer(String answer, int questionId);
}
