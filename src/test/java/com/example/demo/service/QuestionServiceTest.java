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
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.demo.model.Question;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
class QuestionServiceTest {
	
	//以下のクラスをインスタンス化
	@Autowired
	private QuestionService questionService;
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findAllメソッドで、問題が全件取得できること")
	public void getQuestionAll() throws Exception {
		//問題を全件取得
		ArrayList<Question> queList = questionService.findAll();
		//DBに登録されているデータと、取得したデータが一致しているか確認
		assertThat(queList.get(0).getQuestion(), is("test"));
		assertThat(queList.get(1).getQuestion(), is("test2"));
		assertThat(queList.get(2).getQuestion(), is("test3"));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("registerメソッドで、問題が登録でき、idが取得できていること")
	public void registerQuestion() throws Exception {
		//問題を全件取得
		ArrayList<Question> queList1 = questionService.findAll();
		//questionを渡し、問題を登録
		String question = "testtest";
		int questionId = questionService.register(question);
		//問題を全件取得
		ArrayList<Question> queList2 = questionService.findAll();
		//DBに登録されている答えが1件増えたことを確認
		assertThat(queList2.size() - queList1.size(), is(1));
		//DBの最新のデータが、登録したデータと一致しているか確認
		assertThat(queList2.get(3).getQuestion(), is(question));
		//questionIdが取得できていないことを確認
		assertThat(questionId, notNullValue());
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByIdメソッドに、登録されたidを渡すと、問題が取得できること")
	public void getQuestionWhenRegisterId() throws Exception {
		//登録されているidを渡し、問題を取得
		Question question = questionService.findById(1);
		//id = 1に紐づく問題が取得できていることを確認
		assertThat(question.getQuestion(), is("test"));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByIdメソッドに、登録されていないidを渡すと、問題が取得できないこと")
	public void notGetQuestionWhenNotRegisterId() throws Exception {
		//登録されていないidを渡し、問題を取得
		Question question = questionService.findById(5);
		//問題が取得できていないことを確認
		assertNull(question);
	}

	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteメソッドに、登録されているidを渡すと、問題が削除できること")
	public void deleteQuestionWhenRegisterId() throws Exception {
		//問題を全件取得
		ArrayList<Question> queList1 = questionService.findAll();
		//登録されているidを渡し、問題を削除
		questionService.delete(1);
		//問題を全件取得
		ArrayList<Question> queList2 = questionService.findAll();
		//DBに登録されているデータが1件減ったことを確認
		assertThat(queList1.size() - queList2.size(), is(1));
		//id = 1に紐づくデータがDBに存在しないことを確認
		for(Question que : queList2) { 
			assertThat(queList2.get(0).getId(), is(not(1)));
		}
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteメソッドに、登録されていないidを渡すと、問題が削除できないこと")
	public void notDeleteQuestionWhenNotRegisterId() throws Exception {
		//問題を全件取得
		ArrayList<Question> queList1 = questionService.findAll();
		//登録されていないidを渡して、問題を削除
		questionService.delete(5);
		//問題を全件取得
		ArrayList<Question> queList2 = questionService.findAll();
		//DBに登録されている問題の数が減っていないことを確認
		assertThat(queList1.size() - queList2.size(), is(0));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("updateメソッドに、登録されているidを渡すと、問題が更新できること")
	public void updateQuestionWhenRegisterId() throws Exception {
		//登録されているidと、questionを渡し、問題を更新
		questionService.update(1, "testtest");
		//問題を全件取得
		ArrayList<Question> queList = questionService.findAll();
		//id = 1 に紐づく問題が、更新されていることを確認
		assertThat(queList.get(0).getQuestion(), is("testtest"));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("updateメソッドに、登録されていないidを渡すと、問題が更新できないこと")
	public void notUpdateQuestionWhenNotRegisterId() throws Exception {
		//登録されていないidと、questionを渡し、問題を更新
		questionService.update(4, "testtest");
		//問題を全件取得
		ArrayList<Question> queList = questionService.findAll();
		//取得したデータからquestionだけ取り出し、リスト化
		ArrayList<String> questions = new ArrayList<>();
		for(int i = 0; i < queList.size(); i++) {
			questions.add(queList.get(i).getQuestion());
		}
		//リストに、更新した内容が含まれていないことを確認
		assertThat(questions, hasItems(not("testtest")));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findAllOrderByRandメソッドで、問題が全件取得できること")
	public void getQuestionAllOrderByRand() throws Exception {
		//問題をランダムに全件取得
		ArrayList<Question> queList = questionService.findAllOrderByRand();
		//取得したデータからquestionだけ取り出し、リスト化
		ArrayList<String> questions = new ArrayList<>();
		for(int i = 0; i < queList.size(); i++) {
			questions.add(queList.get(i).getQuestion());
		}
		//DBに登録されているデータが全て取得できているか確認
		assertThat(questions, hasItems("test"));
		assertThat(questions, hasItems("test2"));
		assertThat(questions, hasItems("test3"));
	}
}
