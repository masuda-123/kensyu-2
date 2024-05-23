function checkValidation() {
	/*各画面オブジェクト*/
	const userName = document.getElementById('userName');
	const password = document.getElementById('password');
	const passwordConfirm = document.getElementById('passwordConfirm');
	const reg = /^[a-zA-Z0-9]+$/;
	let message = "";
	/*入力値チェック*/
	if(userName.value ==""){
		message += "※ユーザー名が未入力です\n";
	}else if(!reg.test(userName.value)){
		message += "※ユーザー名は半角英数字で入力してください\n";
	}
	if(password.value == ""){
		message += "※パスワードが未入力です\n";
	}else if(!reg.test(password.value)){
		message += "※パスワードは半角英数字で入力してください\n";
	}
	}else if(password.value.length < 8){
		message += "※パスワードは8文字以上で入力してください\n";
	}
	if(passwordConfirm.value == ""){
		message += "※確認用パスワードが未入力です\n";
	}else if(passwordConfirm.value != password.value){
		message += "※パスワードと確認用パスワードが一致していません\n";
	}
	if(message.length > 0){
		alert(message);
		return false;
	}
};