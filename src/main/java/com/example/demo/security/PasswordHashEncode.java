package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordHashEncode {
	//ハッシュ化されたパスワードを生成
	public String encode(String plain_password) {
		 String hash_password = new BCryptPasswordEncoder().encode(plain_password);
		 return hash_password;
	}
}