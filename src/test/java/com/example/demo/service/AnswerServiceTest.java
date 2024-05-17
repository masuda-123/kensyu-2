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
import com.example.demo.model.Answer;
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
class AnswerServiceTest {
	
	@Autowired
	private AnswerService answerService;
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findAllメソッドで、答えが全件取得できること")
	public void getAnswerAll() throws Exception {
		ArrayList<Answer> ansList = answerService.findAll();
		assertThat(ansList.get(0).getAnswer(), is("test"));
		assertThat(ansList.get(1).getAnswer(), is("test2"));
		assertThat(ansList.get(2).getAnswer(), is("test3"));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByQuestionIdメソッドに、登録されたquestions_idを渡すと、答えが取得できること")
	public void getAnswerWhenRegisterQuestionId() throws Exception {
		ArrayList<Answer> ansList = answerService.findByQuestionId(1);
		assertThat(ansList.get(0).getAnswer(), is("test"));
		assertThat(ansList.get(1).getAnswer(), is("test2"));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByQuestionIdメソッドに、登録されていないquestions_idを渡すと、答えが取得できないこと")
	public void notGetAnswerWhenRegisterNotQuestionId() throws Exception {
		ArrayList<Answer> ansList = answerService.findByQuestionId(2);
		assertTrue(ansList.isEmpty());
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("registerメソッドで、questionIdを渡すと、答えが登録できること")
	public void registerAnswerWhenQuestionId() throws Exception {
		ArrayList<Answer> ansList1 = answerService.findAll();
		String answer = "testtest";
		answerService.register(answer, 1);
		ArrayList<Answer> ansList2 = answerService.findAll();
		assertThat(ansList2.size() - ansList1.size(), is(1));
		assertThat(ansList2.get(3).getAnswer(), is(answer));
	}

}
