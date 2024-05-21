package com.example.demo.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.demo.Kensyu2Application;
import com.example.demo.model.History;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import jakarta.transaction.Transactional;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Kensyu2Application.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
class HistoryServiceTest {
	
	@Autowired
	private HistoryService historyService;
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByUserIdメソッドに、登録されていないuser_idを渡すと、履歴が取得できないこと")
	public void notGetHistoryWhenNotRegisterUserId() throws Exception {
		//登録されていないuser_idを渡し、履歴を取得
		ArrayList<History> hisList = historyService.findByUserId(3);
		//履歴が取得できていないことを確認
		assertTrue(hisList.isEmpty());
	}

	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByUserIdメソッドに、登録されているuser_idを渡すと、履歴が作成日順に取得できること")
	public void getHistoryWhenRegisterUserId() throws Exception {
		//登録されているuser_idを渡し、履歴を取得
		ArrayList<History> hisList = historyService.findByUserId(1);
		//DBにあるuser_id = 1と紐づくデータと、取得したデータが一致しているか確認
		assertThat(hisList.get(0).getPoint(), is(20));
		assertThat(hisList.get(1).getPoint(), is(10));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("registerメソッドで、履歴が登録できること")
	public void registerHistory() throws Exception {
		//user_id = 1と一致するデータを取得
		ArrayList<History> hisList1 = historyService.findByUserId(1);
		//user_idと、pointを渡して、履歴を登録
		historyService.register(1, 50);
		//user_id = 1と一致するデータを取得
		ArrayList<History> hisList2 = historyService.findByUserId(1);
		//user_id = 1と紐づくデータが、1件増えたことを確認
		assertThat(hisList2.size() - hisList1.size(), is(1));
		//user_id = 1と紐づく最新のデータが、登録したデータと一致することを確認
		assertThat(hisList2.get(2).getPoint(), is(50));
	}
}
