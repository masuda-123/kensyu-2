package com.example.demo.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;

@Repository
@Mapper
public interface QuestionMapper {
	ArrayList<Question> getQuestion();
	void registerQuestion(String question);
	int findMaxId();
	}