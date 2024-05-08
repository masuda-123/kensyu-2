package com.example.demo.securingweb;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordHashEncode {

	
	public String encode(String plain_password) {
		 String hash_password = new BCryptPasswordEncoder().encode(plain_password);
		 return hash_password;
	}
	
	
}