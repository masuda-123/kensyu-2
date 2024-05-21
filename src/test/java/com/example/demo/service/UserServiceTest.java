package com.example.demo.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.Kensyu2Application;
import com.example.demo.model.User;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Kensyu2Application.class)
class UserServiceTest {
	
	//以下のクラスをインスタンス化
	@Autowired
	private UserService userService;
	
	@Test
	@DisplayName("findByIdメソッドに、登録されていないuserIdを引数として渡した場合、ユーザーが取得できないこと")
	public void notGetUserWhenNotRegisterUserId() throws Exception {
		//登録されていないidを渡して、ユーザーを取得しようとする
		User user = userService.findById(10);
		//ユーザーが取得できないことを確認
		assertNull(user);
	}

	@Test
	@DisplayName("findByIdメソッドに、登録されているuserIdを引数として渡した場合、ユーザーを取得できること")
	public void getUserWhenRegisterUserId() throws Exception {
		//登録されているidを渡して、ユーザーを取得
		User user = userService.findById(1);
		//ユーザーが取得できることを確認
		assertThat(user.getId(), is(1));
		assertThat(user.getName(), is("testuser"));
	}
	
	@Test
	@DisplayName("loadUserByUsernameメソッドに、登録されていないuserIdを渡した場合、UserDetailsが取得できないこと")
	public void  notGetUserDetailsWhenNotRegisterUserId() throws Exception{
		UserDetails user = null;
		try {
			//登録されていないidを渡して、ユーザーを取得しようとする
			user = userService.loadUserByUsername("10");
		} catch(UsernameNotFoundException e) {
			//例外が発生することを確認
			assertThat(e.getMessage(), is("User_id = 10 was not found in the database"));
		} finally {
			//ユーザーが取得できないことを確認
			assertNull(user);
		}
	}
	
	@Test
	@DisplayName("loadUserByUsernameメソッドに、文字列を渡した場合、UserDetailsが取得できないこと")
	public void notGetUserDetailsWhentext() throws Exception{
		UserDetails user = null;
		try {
			//文字列を渡して、ユーザーを取得しようとする
			user = userService.loadUserByUsername("あいうえお");
		} catch(UsernameNotFoundException e) {
			//例外が発生することを確認
			assertThat(e.getMessage(), is("User_id = あいうえお was not found in the database"));
		} finally {
			//ユーザーが取得できないことを確認
			assertNull(user);
		}
	}
	
	@Test
	@DisplayName("loadUserByUsernameメソッドに、小数を渡した場合、UserDetailsが取得できないこと")
	public void notGetUserDetailsWhenDecimal() throws Exception{
		UserDetails user = null;
		try {
			//小数を渡して、ユーザーを取得しようとする
			user = userService.loadUserByUsername("0.5");
		} catch(UsernameNotFoundException e) {
			//例外が発生することを確認
			assertThat(e.getMessage(), is("User_id = 0.5 was not found in the database"));
		} finally {
			//ユーザーが取得できないことを確認
			assertNull(user);
		}
	}
	
	@Test
	@DisplayName("loadUserByUsernameメソッドに、負の数を渡した場合、UserDetailsが取得できないこと")
	public void notGetUserDetailsWhenNegativeNumber() throws Exception{
		UserDetails user = null;
		try {
			//負の数を渡して、ユーザーを取得しようとする
			user = userService.loadUserByUsername("-1");
		} catch(UsernameNotFoundException e) {
			//例外が発生することを確認
			assertThat(e.getMessage(), is("User_id = -1 was not found in the database"));
		} finally {
			//ユーザーが取得できないことを確認
			assertNull(user);
		}
	}
	
	@Test
	@DisplayName("loadUserByUsernameメソッドに、登録されているuserIdを渡した場合、UserDetailsが取得できること")
	public void getUserDetailsWhenRegisterUserId() throws Exception {
		UserDetails user = null;
		try {
			//登録されているuserIdを渡して、ユーザーを取得しようとする
			user = userService.loadUserByUsername("1");
		} catch(UsernameNotFoundException e) {
			assertNull(e.getMessage());
		} finally {
			//ユーザーが取得できることを確認
			assertThat(user.getUsername(), is("1"));
		}
	}

}
