package com.example.demo.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				// アクセス制限をかけない
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/login").permitAll()
				//すべての要求で、ユーザーの認証を必要とする
				.anyRequest().authenticated()
			)
			.formLogin((login) -> login
				.loginPage("/login")
				.usernameParameter("userId")
				.passwordParameter("password")
				// ログイン画面
				.loginPage("/login")
				// ログイン失敗時のURL
				.failureUrl("/login")
				// ログインに成功した場合の遷移先
				.defaultSuccessUrl("/top")
				.permitAll()
			)
			.logout((logout) -> logout
				// ログアウトのURL
				.logoutUrl("/logout")
				// ログアウトした場合の遷移先
				.logoutSuccessUrl("/login")
			)
			.userDetailsService(userDetailsService);
		return http.build();
	}
	
	// パスワードのハッシュ化
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
