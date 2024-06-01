package com.example.demo.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.demo.model.User;
import com.example.demo.security.PasswordEncrypter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@Transactional
class UserServiceTest {
	
	//以下のクラスをインスタンス化
	@Autowired
	private UserService userService;
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByIdメソッドに、登録されていないuserIdを引数として渡した場合、ユーザーが取得できないこと")
	public void notGetUserWhenNotRegisterUserId() throws Exception {
		//登録されていないidを渡して、ユーザーを取得しようとする
		User user = userService.findById(10);
		//ユーザーが取得できないことを確認
		assertNull(user);
	}

	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findByIdメソッドに、登録されているuserIdを引数として渡した場合、ユーザーを取得できること")
	public void getUserWhenRegisterUserId() throws Exception {
		//登録されているidを渡して、ユーザーを取得
		User user = userService.findById(1);
		//ユーザーが取得できることを確認
		assertThat(user.getId(), is(1));
		assertThat(user.getName(), is("testuser1"));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
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
	@DatabaseSetup("../dbunit/sampleData.xml")
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
	@DatabaseSetup("../dbunit/sampleData.xml")
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
	@DatabaseSetup("../dbunit/sampleData.xml")
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
	@DatabaseSetup("../dbunit/sampleData.xml")
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
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("findAllメソッドで、削除されていないユーザーが全件取得できること")
	public void getUserAll() throws Exception {
		//ユーザーを全件取得
		ArrayList<User> userList = userService.findAll();
		//DBに登録されているデータと、取得したデータが一致しているか確認
		assertThat(userList.get(0).getName(), is("testuser1"));
		assertThat(userList.get(1).getName(), is("testuser2"));
		assertThat(userList.get(2).getName(), is("testuser3"));
		//取得したデータに、削除されているユーザーが含まれていないことを確認
		for(User user : userList) { 
			assertThat(user.getName(), is(not("testuser4")));
		}
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("registerメソッドで、ユーザーが登録できること")
	public void registerUser() throws Exception {
		//ユーザーを全件取得
		ArrayList<User> userList1 = userService.findAll();
		//ユーザー名、パスワード、管理者権限を渡し、問題を登録
		String userName = "testuser";
		String password = "testtest";
		int adminFlag = 1;
		userService.register(userName, password, adminFlag);
		//ユーザーを全件取得
		ArrayList<User> userList2 = userService.findAll();
		//パスワードをデコードするためインスタンス化する
		PasswordEncrypter passEncrypter = new PasswordEncrypter();
		//DBに登録されている答えが1件増えたことを確認
		assertThat(userList2.size() - userList1.size(), is(1));
		//DBの最新のデータが、登録したデータと一致しているか確認
		assertThat(userList2.get(3).getName(), is(userName));
		assertThat(passEncrypter.decrypt(userList2.get(3).getPassword()), is(password));
		assertThat(userList2.get(3).getAdmin_flag(), is(adminFlag));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("updateメソッドに、登録されているidを渡すと、ユーザー情報が更新できること")
	public void updateUserWhenRegisterId() throws Exception {
		//登録されているidと、パスワード、管理者権限を渡し、ユーザー情報を更新
		String password = "testtest5";
		int adminFlag = 0;
		userService.update(1, password, adminFlag);
		//ユーザーを全件取得
		ArrayList<User> userList = userService.findAll();
		//パスワードをデコードするためインスタンス化する
		PasswordEncrypter passEncrypter = new PasswordEncrypter();
		//id = 1 に紐づくユーザー情報が、更新されていることを確認
		assertThat(passEncrypter.decrypt(userList.get(0).getPassword()), is(password));
		assertThat(userList.get(0).getAdmin_flag(), is(adminFlag));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("updateメソッドに、登録されていないidを渡すと、ユーザー情報が更新できないこと")
	public void notUpdateUserWhenNotRegisterId() throws Exception {
		//登録されていないidと、パスワード、管理者権限を渡し、ユーザー情報を更新
		String password = "testtest5";
		int adminFlag = 0;
		userService.update(10, password, adminFlag);
		//ユーザーを全件取得
		ArrayList<User> userList = userService.findAll();
		//パスワードをデコードするためインスタンス化する
		PasswordEncrypter passEncrypter = new PasswordEncrypter();
		//取得したデータからpasswordだけ取り出し、リスト化
		ArrayList<String> passwords = new ArrayList<>();
		for(int i = 0; i < userList.size(); i++) {
			passwords.add(passEncrypter.decrypt(userList.get(i).getPassword()));
		}
		//リストに、更新した内容が含まれていないことを確認
		assertThat(passwords, hasItems(not("testtest5")));
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteメソッドに、登録されているidを渡すと、ユーザーが削除できること")
	public void deleteUserWhenRegisterId() throws Exception {
		//ユーザーを全件取得
		ArrayList<User> userList1 = userService.findAll();
		//登録されているidを渡し、問題を削除
		userService.delete(1);
		//問題を全件取得
		ArrayList<User> userList2 = userService.findAll();
		//取得したデータが1件減ったことを確認
		assertThat(userList1.size() - userList2.size(), is(1));
		//id = 1に紐づくデータが存在しないことを確認
		for(User user : userList2) { 
			assertThat(user.getId(), is(not(1)));
		}
	}
	
	@Test
	@DatabaseSetup("../dbunit/sampleData.xml")
	@DisplayName("deleteメソッドに、登録されていないidを渡すと、ユーザーが削除できないこと")
	public void notDeleteUserWhenNotRegisterId() throws Exception {
		//ユーザーを全件取得
		ArrayList<User> userList1 = userService.findAll();
		//登録されていないidを渡し、問題を削除
		userService.delete(10);
		//問題を全件取得
		ArrayList<User> userList2 = userService.findAll();
		//取得したデータが減っていないことを確認
		assertThat(userList1.size() - userList2.size(), is(0));
	}

}
