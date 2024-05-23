package com.example.demo.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//パフォーマンスの最適化機能を無効にする
	    HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
	    requestCache.setMatchingRequestParameterName(null);
		http
			.authorizeHttpRequests((authorize) -> authorize
				// アクセス制限をかけない要求
				.requestMatchers("/favicon.ico", "/resources/**", "/error").permitAll()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				//上記以外のすべての要求で、ユーザーの認証を必要とする
				.anyRequest().authenticated()
			)
			.formLogin((login) -> login
				.usernameParameter("userId")
				.passwordParameter("password")
				// ログイン画面
				.loginPage("/login").permitAll()
				// ログイン失敗時のURL
				.failureUrl("/login").permitAll()
				// ログインに成功した場合の遷移先
				.defaultSuccessUrl("/top")
			)
			.logout((logout) -> logout
				// ログアウトのURL
				.logoutUrl("/logout")
				// ログアウトした場合の遷移先
				.logoutSuccessUrl("/login").permitAll()
			)
	       .requestCache((cache) -> cache
	               .requestCache(requestCache)
	        );
		return http.build();
	}
	
	//ユーザーが入力したパスワードをハッシュ化する
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
