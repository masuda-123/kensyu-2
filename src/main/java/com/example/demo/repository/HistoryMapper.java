package com.example.demo.repository;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.History;

@Mapper
public interface HistoryMapper {
	//userIdをもとに履歴を取得（採点時間の昇順で）
	ArrayList<History> findByUserId(int userId);
	
	//履歴を登録
	void register(int userId, int point);
	
	//userIdをもとに履歴を削除
	@Transactional
	public void delete(int userId);
}
