function checkValidation() {
	//フォームのinputタグの要素を取得
	const userName = document.getElementById('userName');
	const password = document.getElementById('password');
	const passwordConfirm = document.getElementById('passwordConfirm');
	//正規表現として半角英数字を設定
	const reg = /^[a-zA-Z0-9]+$/;
	let message = "";
	
	//ユーザー名が空だった場合
	if(userName.value ==""){
		message += "※ユーザー名が未入力です\n";
	//ユーザー名が半角英数字でなかった場合
	}else if(!reg.test(userName.value)){
		message += "※ユーザー名は半角英数字で入力してください\n";
	}
	//パスワードが空だった場合
	if(password.value == ""){
		message += "※パスワードが未入力です\n";
	//パスワードが半角英数字でなかった場合
	}else if(!reg.test(password.value)){
		message += "※パスワードは半角英数字で入力してください\n";
	//パスワードが8文字より少なかった場合
	}else if(password.value.length < 8){
		message += "※パスワードは8文字以上で入力してください\n";
	}
	//確認用パスワードが空だった場合
	if(passwordConfirm.value == ""){
		message += "※確認用パスワードが未入力です\n";
	//確認用パスワードが、パスワードと一致していなかった場合
	}else if(passwordConfirm.value != password.value){
		message += "※パスワードと確認用パスワードが一致していません\n";
	}
	//エラーメッセーがある場合、アラートを表示し、画面遷移させない
	if(message.length > 0){
		alert(message);
		return false;
	}
};