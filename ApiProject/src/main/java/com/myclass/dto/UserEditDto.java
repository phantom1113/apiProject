package com.myclass.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDto {
	private int id;
	
	@NotEmpty(message = "Email không được bỏ trống")
	private String email;
	
	private String password;
	
	@NotEmpty(message = "Fullname không được bỏ trống")
	private String fullName;
	
	private String avatar;
	
	@NotNull(message = "Role không được bỏ trống")
	private int roleId;
	
	private String roleDesc;
	private String roleName;

	public UserEditDto(int id, String email, String password, String fullName, String avatar, int roleId, String roleDesc,
			String roleName) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.avatar = avatar;
		this.roleId = roleId;
		this.roleDesc = roleDesc;
		this.roleName = roleName;
	}

	public UserEditDto(String email, String password, String roleName) {
		super();
		this.email = email;
		this.password = password;
		this.roleName = roleName;
	}

	public UserEditDto(int id, String email, String password, String fullName, String avatar, String roleName) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.avatar = avatar;
		this.roleName = roleName;
	}

	public UserEditDto(int id, String fullName) {
		super();
		this.id = id;
		this.fullName = fullName;
	}
}
