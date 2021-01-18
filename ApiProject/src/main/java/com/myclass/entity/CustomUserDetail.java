package com.myclass.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Class dùng để custom thông tin của UserDetail dung xác thực và phân quyền
public class CustomUserDetail extends org.springframework.security.core.userdetails.User implements UserDetails{
	private static final long serialVersionUID = 1L;

	private String avatar;
	
	private int id;
	
	private String fullName;
	
	private String roleName;
	
	public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
