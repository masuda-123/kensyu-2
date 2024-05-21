package com.example.demo.other;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@SpringBootTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class
})
class ValidationTest {
	
	//以下のクラスをインスタンス化
	@Autowired
	private Validation val;
	
	@Test
	@DisplayName("501文字以上の問題文が入力された場合、エラーメッセージが返される")
	void maxLengthOverQuestion() {
		//501文字を問題に格納
		String question = "z83IrhzrhQRiZw6vWEACLUPryKDiCZiNHwTVm6XSvaTvrelRJdmO0U5hpUqv1ff0HiBuXdSxPiCCY1zZykwWgOfkEv"
				+ "YmApC93DJaIBmkDOfCcOFltAsMKLdBSnww6HfF6KWiYeHTlVzDlCmtxe0miKBFr8wVOcQPqsE4j6bn1aUCShX6DHRoHjfDWr2qv"
				+ "s3tYwJTLHSit4d533eKJe2c3nSf9I9zVeVfu7UFOKwG1PiKH2eaOP6xCrp9LQlwOx4MwAJooCXpprrw5LwsHfqDD2YIgGZPNQRLy"
				+ "6ZaF6K5Im5j2mcV7uuVbigxDQNBD6g5b97gKB8ijMCBJ5b5Tzja09sNL9muwaxhroejO6Yk3Ey5grG0ZCan0UTPgNGQHpKd6NmZi"
				+ "bS15RpCpYItHQvo0986qeQ4XEe9jGflb9vly7D26I6S3VFKNhl9EhzuQ8EeeGfEgvV6vm7JUWCmZR9UgjFLWHA5w6TlcPOcxClfEyJz3O0CzOInL";
		String[] answers = {"あいう"};
		//validateメソッドに問題文と答えを渡して、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//エラーメッセージが取得できることを確認
		assertThat(errorMessage, is("※問題の文字数が制限（500文字）を超えています。<br>"));
	}
	
	@Test
	@DisplayName("201文字以上の答えが入力された場合、エラーメッセージが返される")
	void maxLengthOverAnswers() {
		String question = "あいう";
		//201文字を答えに格納
		String[] answers = {"8JU52UJYuy49IHP12a1b9JuFDCEAv5WqYjxN96U9lkpXCkSyX9qb6ckCPzLcl7ZynauHcgjdlodD"
				+ "CkH2C4Ao3gmvjwwY5RbJlz3xaFfxCVxLRSZTSa2FVMVOAe5OKfuSPoAz62R3CvilDt31cpT5cGtpn7CuUPhZ2PhmmwpP"
				+ "K3SJ4CGzDM2iR8zFD9d5rNlVoGwCo7PQ1", "aiu"};
		//validateメソッドに問題文と答えを渡して、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//エラーメッセージが取得できていることを確認
		assertThat(errorMessage, is("※答え1の文字数が制限（200文字）を超えています。<br>"));
	}
	
	@Test
	@DisplayName("問題が入力されなかった場合、エラーメッセージが返される")
	void nullQuestion() {
		//空文字を問題に格納
		String question = "";
		String[] answers = {"aiu"};
		//validateメソッドに問題文と答えを渡して、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//エラーメッセージが取得できていることを確認
		assertThat(errorMessage, is("※問題を入力してください。<br>"));
	}
	
	@Test
	@DisplayName("答えが入力されなかった場合、エラーメッセージが返される")
	void nullAnswers() {
		String question = "あいう";
		//空文字を含む答えの配列を作成
		String[] answers = {"", "aiu"};
		//validateメソッドに問題文と答えを渡して、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//エラーメッセージが取得できていることを確認
		assertThat(errorMessage, is("※答え1が未入力です。<br>"));
	}
	
	@Test
	@DisplayName("問題がブランクだった場合、エラーメッセージが返される")
	void blankQuestion() {
		//空白を問題に格納
		String question = " ";
		String[] answers = {"aiu"};
		//validateメソッドに問題文と答えを渡して、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//エラーメッセージが取得できていることを確認
		assertThat(errorMessage, is("※問題を入力してください。<br>"));
	}
	
	@Test
	@DisplayName("答えがブランクだった場合、エラーメッセージが返される")
	void blankAnswers() {
		String question = "あいう";
		//空白を含む答えの配列を作成
		String[] answers = {" ", "aiu"};
		//validateメソッドに問題文と答えを渡して、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//エラーメッセージが取得できていることを確認
		assertThat(errorMessage, is("※答え1が未入力です。<br>"));
	}
	
	@Test
	@DisplayName("問題文が500文字以下、答えが200文字以下で入植されていた場合、エラーメッセージが返されない")
	void notError() {
		String question = "あいう";
		String[] answers = {"aiu "};
		//validateメソッドに問題文と答えを渡して、エラーメッセージを取得
		String errorMessage = val.validate(question, answers);
		//エラーメッセージが空であることを確認
		assertThat(errorMessage, is(""));
	}
}
