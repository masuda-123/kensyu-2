package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((requests) -> requests
				// アクセス制限をかけない
				.requestMatchers("/"
						, "/login?error"
						,"/css/**")
				.permitAll()
				.anyRequest().authenticated()
				)
		.formLogin((login) -> login
				.usernameParameter("userId")
				.passwordParameter("password")
				// ログインを実行するページ
				.loginProcessingUrl("/login")
				// ログイン画面
				.loginPage("/login")
				// ログイン失敗時のURL
				.failureUrl("/login")
				// ログインに成功した場合の遷移先
				.successForwardUrl("/top")
				.permitAll()
				)
		.logout((logout) -> logout
				// ログアウトのURL
				.logoutUrl("/logout")
				// ログアウトした場合の遷移先
				.logoutSuccessUrl("/login")
				.permitAll());
		return http.build();
		}
	
	// パスワードのハッシュ化
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
