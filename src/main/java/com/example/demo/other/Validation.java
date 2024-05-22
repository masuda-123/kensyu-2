package com.example.demo.other;

import org.springframework.stereotype.Component;

@Component
public class Validation {
	public static final int MAX_QUESTION = 500;
	public static final int MAX_ANSWER = 200;
	
	public String validate(String question, String[] answers) {
		
		String errorMessage = "";
		boolean isNewLine = false;
		
		if(question.isEmpty() || question.isBlank()) { //問題文が空もしくはブランクだった場合
			//エラーメッセージに文字列を追加
			errorMessage += "※問題を入力してください。";
			//改行するようtrueを格納
			isNewLine = true;
		} else if(question.length() > MAX_QUESTION) { //問題文の文字数が500文字を超えていた場合
			//エラーメッセージに文字列を追加
			errorMessage += "※問題の文字数が制限（500文字）を超えています。";
			//改行するようtrueを格納
			isNewLine = true;
		}
		
		if (isNewLine) { // trueだった場合
			//エラーメッセージにbrタグを追加し、改行させる
			errorMessage += "<br>";
		}
		
		//答えが空だった場合
		if(answers.length == 0) {
			//エラーメッセージに文字列を追加
			errorMessage += "※答え1が未入力です。" + "<br>" ;
		}
		
		//答えの数だけ処理を繰り返す
		for(int i = 0; i < answers.length; i++) {
			//初期値に戻す
			isNewLine = false;
			if(answers[i].isEmpty() || answers[i].isBlank()) { 	//答えが空もしくはブランクだった場合
				//エラーメッセージに文字列を追加
				errorMessage += "※答え" + (i+1) + "が未入力です。";
				//改行するようtrueを格納
				isNewLine = true;
			}else if(answers[i].length() > MAX_ANSWER) { //答えの文字数が200文字を超えていた場合
				//エラーメッセージに文字列を追加
				errorMessage += "※答え" + (i+1) + "の文字数が制限（200文字）を超えています。";
				//改行するようtrueを格納
				isNewLine = true;
			}
			if (isNewLine) { // trueだった場合
				//エラーメッセージにbrタグを追加し、改行させる
				errorMessage += "<br>";
			}
		}
		return errorMessage;
	}
}
