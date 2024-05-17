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
import com.example.demo.model.Question;
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
class QuestionServiceTest {
	
	@Autowired
	private QuestionService questionService;
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findAllメソッドで、問題が全件取得できること")
	public void getQuestionAll() throws Exception {
		ArrayList<Question> queList = questionService.findAll();
		assertThat(queList.get(0).getQuestion(), is("test"));
		assertThat(queList.get(1).getQuestion(), is("test2"));
		assertThat(queList.get(2).getQuestion(), is("test3"));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("registerメソッドで、問題が登録できること")
	public void registerQuestion() throws Exception {
		String question = "testtest";
		int questionId = questionService.register(question);
		ArrayList<Question> queList = questionService.findAll();
		assertThat(queList.get(3).getQuestion(), is(question));
		assertThat(questionId, notNullValue());
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByIdメソッドに、登録されたidを渡すと、問題が取得できること")
	public void getQuestionWhenRegisterId() throws Exception {
		Question question = questionService.findById(1);
		assertThat(question.getQuestion(), is("test"));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByIdメソッドに、登録されていないidを渡すと、問題が取得できないこと")
	public void notGetQuestionWhenNotRegisterId() throws Exception {
		Question question = questionService.findById(5);
		assertNull(question);
	}

	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteメソッドに、登録されているidを渡すと、問題が削除できること")
	public void deleteQuestionWhenRegisterId() throws Exception {
		ArrayList<Question> queList1 = questionService.findAll();
		questionService.delete(1);
		ArrayList<Question> queList2 = questionService.findAll();
		assertThat(queList1.size() - queList2.size(), is(1));
		assertThat(queList2.get(0).getId(), is(not(1)));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteメソッドに、登録されていないidを渡すと、問題が削除できないこと")
	public void notDeleteQuestionWhenNotRegisterId() throws Exception {
		ArrayList<Question> queList1 = questionService.findAll();
		questionService.delete(5);
		ArrayList<Question> queList2 = questionService.findAll();
		assertThat(queList2.size() - queList1.size(), is(0));
		assertThat(queList2.get(0).getId(), is(1));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("updateメソッドに、登録されているidを渡すと、問題が更新できること")
	public void updateQuestionWhenRegisterId() throws Exception {
		questionService.update(1, "testtest");
		ArrayList<Question> queList = questionService.findAll();
		assertThat(queList.get(0).getQuestion(), is("testtest"));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("updateメソッドに、登録されていないidを渡すと、問題が更新できないこと")
	public void notUpdateQuestionWhenNotRegisterId() throws Exception {
		questionService.update(4, "testtest");
		ArrayList<Question> queList = questionService.findAll();
		ArrayList<String> questions = new ArrayList<>();
		for(int i = 0; i < queList.size(); i++) {
			questions.add(queList.get(i).getQuestion());
		}
		assertThat(questions, hasItems(not("testtest")));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findAllOrderByRandメソッドで、問題が全件取得できること")
	public void getQuestionAllOrderByRand() throws Exception {
		ArrayList<Question> queList = questionService.findAllOrderByRand();
		ArrayList<String> questions = new ArrayList<>();
		for(int i = 0; i < queList.size(); i++) {
			questions.add(queList.get(i).getQuestion());
		}
		assertThat(questions, hasItems("test"));
		assertThat(questions, hasItems("test2"));
		assertThat(questions, hasItems("test3"));
	}
}
