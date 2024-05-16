package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.History;
import com.example.demo.repository.HistoryMapper;

@Service
public class HistoryService {
	
	//以下のクラスをインスタンス化
	@Autowired
	private HistoryMapper historyMapper;
	
	//userIdをもとに履歴を取得（採点時間の昇順で）
	@Transactional
	public ArrayList<History> findByUserId(int userId) {
		return historyMapper.findByUserId(userId);
	}
	
	//履歴を登録
	@Transactional
	public void register(int userId, int point) {
		historyMapper.register(userId, point);
	}

}
