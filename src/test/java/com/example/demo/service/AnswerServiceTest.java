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
	
	//以下のクラスをインスタンス化
	@Autowired
	private AnswerService answerService;
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findAllメソッドで、答えが全件取得できること")
	public void getAnswerAll() throws Exception {
		//答えを全件取得
		ArrayList<Answer> ansList = answerService.findAll();
		//DBに登録されているデータと、取得したデータが一致しているか確認
		assertThat(ansList.get(0).getAnswer(), is("test"));
		assertThat(ansList.get(1).getAnswer(), is("test2"));
		assertThat(ansList.get(2).getAnswer(), is("test3"));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByQuestionIdメソッドに、登録されたquestions_idを渡すと、答えが取得できること")
	public void getAnswerWhenRegisterQuestionId() throws Exception {
		//登録されているquestions_idを渡し、一致するレコードを取得
		ArrayList<Answer> ansList = answerService.findByQuestionId(1);
		//DBにあるquestions_id = 1と紐づくデータと、取得したデータが一致しているか確認
		assertThat(ansList.get(0).getAnswer(), is("test"));
		assertThat(ansList.get(1).getAnswer(), is("test2"));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByQuestionIdメソッドに、登録されていないquestions_idを渡すと、答えが取得できないこと")
	public void notGetAnswerWhenRegisterNotQuestionId() throws Exception {
		//登録されていないquestions_idを渡し、一致するレコードを取得
		ArrayList<Answer> ansList = answerService.findByQuestionId(2);
		//データが取得できていないことを確認
		assertTrue(ansList.isEmpty());
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("registerメソッドで、questionIdを渡すと、答えが登録できること")
	public void registerAnswerWhenQuestionId() throws Exception {
		//答えを全件取得
		ArrayList<Answer> ansList1 = answerService.findAll();
		//answerとquestions_idを渡し、答えを登録
		String answer = "testtest";
		answerService.register(answer, 1);
		//答えを全件取得
		ArrayList<Answer> ansList2 = answerService.findAll();
		//DBに登録されている答えが1件増えたことを確認
		assertThat(ansList2.size() - ansList1.size(), is(1));
		//最新の答えが、登録した答えと一致しているか確認
		assertThat(ansList2.get(3).getAnswer(), is(answer));
		
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteByQuestionIdメソッドに、登録されていないquestions_idを渡すと、答えが削除できないこと")
	public void notDeleteAnswerWhenNotRegisterQuestionId() throws Exception {
		//答えを全件取得
		ArrayList<Answer> ansList1 = answerService.findAll();
		//登録されていないquestions_idを渡し、答えを削除
		answerService.deleteByQuestionId(2);
		//答えを全件取得
		ArrayList<Answer> ansList2 = answerService.findAll();
		//DBに登録されている答えが減っていないことを確認
		assertThat(ansList1.size() - ansList2.size(), is(0));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteByQuestionsIdメソッドに、登録されているquestions_idを渡すと、答えが削除できること")
	public void deleteAnswerWhenRegisterQuestionId() throws Exception {
		//答えを全件取得
		ArrayList<Answer> ansList1 = answerService.findAll();
		//登録されているquestions_idを渡し、答えを削除
		answerService.deleteByQuestionId(3);
		//答えを全件取得
		ArrayList<Answer> ansList2 = answerService.findAll();
		//DBに登録されている答えが1件減ったことを確認
		assertThat(ansList1.size() - ansList2.size(), is(1));
		//DBに、questions_id = 3と紐づくデータが存在しないことを確認（削除されたことを確認）
		for(Answer ans : ansList2) {
			assertThat(ans.getQuestions_id(), is(not(3)));
		}
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("updateメソッドに、登録されていないidを渡すと、答えが更新できないこと")
	public void notUpdateAnswerWhenNotRegisterId() throws Exception {
		//answerと登録されていないdを渡し、答えを更新
		answerService.update("testtest", 4);
		//答えを全件取得
		ArrayList<Answer> ansList = answerService.findAll();
		//取得したデータからanswerだけ取り出し、リスト化
		ArrayList<String> answers = new ArrayList<>();
		for(int i = 0; i < ansList.size(); i++) {
			answers.add(ansList.get(i).getAnswer());
		}
		//リストに、更新した内容が含まれていないことを確認
		assertThat(answers, hasItems(not("testtest")));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("updateメソッドに、登録されているidを渡すと、答えが更新できること")
	public void updateAnswerWhenRegisterId() throws Exception {
		//answerと登録されているidを渡し、答えを更新
		answerService.update("testtest", 1);
		//答えを全件取得
		ArrayList<Answer> ansList = answerService.findAll();
		//id = 1と紐づくデータが、更新されていることを確認
		assertThat(ansList.get(0).getAnswer(), is("testtest"));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteメソッドに、登録されていないidを渡すと、答えが削除できないこと")
	public void notDeleteAnswerWhenNotRegisterId() throws Exception {
		//答えを全件取得
		ArrayList<Answer> ansList1 = answerService.findAll();
		//登録されていないidを渡し、答えを削除
		answerService.deleteById(5);
		//答えを全件取得
		ArrayList<Answer> ansList2 = answerService.findAll();
		//DBに登録されている答えが減っていないことを確認
		assertThat(ansList1.size() - ansList2.size(), is(0));
	}
	
	@Test
	@Transactional
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteメソッドに、登録されているidを渡すと、答えが削除できること")
	public void deleteAnswerWhenRegisterId() throws Exception {
		//答えを全件取得
		ArrayList<Answer> ansList1 = answerService.findAll();
		//登録されているidを渡し、答えを削除
		answerService.deleteById(1);
		//答えを全件取得
		ArrayList<Answer> ansList2 = answerService.findAll();
		//DBに登録されている答えが1件減っていることを確認
		assertThat(ansList1.size() - ansList2.size(), is(1));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByAnswerメソッドに、登録されていないanswerを渡すと、答えが取得できないこと")
	public void notGetAnswerWhenNotRegisterAnswer() throws Exception {
		//登録されていないanswerを渡し、答えを取得
		ArrayList<Answer> ansList = answerService.findByAnswer("testtest");
		//答えが取得できないことを確認
		assertTrue(ansList.isEmpty());
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByAnswerメソッドに、登録されているanswerを渡すと、答えが取得できること")
	public void getAnswerWhenRegisterAnswer() throws Exception {
		//登録されているanswerを渡し、答えを取得
		ArrayList<Answer> ansList = answerService.findByAnswer("test");
		//渡したanswerと一致するデータが取得できていることを確認
		assertThat(ansList.get(0).getAnswer(), is("test"));
	}

}
