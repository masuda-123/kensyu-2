package com.example.demo.model;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
	
	@Id
	private int id;
	
	private String name;
	
	private String password;
	
	private Timestamp created_at;
	
	private Timestamp updated_at;
	
	private Timestamp deleted_at;
	
	private int admin_flag;
	
	//権限を取得
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//admin_flagが1の場合、ADMINを返す
		if(this.admin_flag == 1) {
			return AuthorityUtils.createAuthorityList("ADMIN");
		//それ以外の場合、USERを返す
		}else {
			return AuthorityUtils.createAuthorityList("USER");
		}
	}
	
	//userIdを取得
	@Override
	public String getUsername() {
		return Integer.toString(this.id);
	}
	
	//アカウントが有効期限内であるか
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//アカウントがロックされていないか
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//資格情報が有効期限内であるか
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 有効なアカウントであるか
	@Override
	public boolean isEnabled() {
		return true;
	}
}
