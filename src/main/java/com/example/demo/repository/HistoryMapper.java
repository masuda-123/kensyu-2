package com.example.demo.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.History;

@Mapper
public interface HistoryMapper {
	//userIdをもとに履歴を取得
	ArrayList<History> findByUserId(int userId);
}
