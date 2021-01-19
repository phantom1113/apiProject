package com.myclass.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private int id;
	
	@NotEmpty(message = "Email không được bỏ trống")
	private String email;
	
	@NotEmpty(message = "Password không được bỏ trống")
	private String password;
	
	@NotEmpty(message = "Fullname không được bỏ trống")
	private String fullName;
	
	@NotEmpty(message = "Avatar không được bỏ trống")
	private String avatar;
	
	@NotNull(message = "Role không được bỏ trống")
	private int roleId;
	
	private String roleDesc;
	private String roleName;

	public UserDto(int id, String email, String password, String fullName, String avatar, int roleId, String roleDesc,
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

	public UserDto(String email, String password, String roleName) {
		super();
		this.email = email;
		this.password = password;
		this.roleName = roleName;
	}

	public UserDto(int id, String email, String password, String fullName, String avatar, String roleName) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.avatar = avatar;
		this.roleName = roleName;
	}

	public UserDto(int id, String fullName, String avatar) {
		super();
		this.id = id;
		this.avatar = avatar;
		this.fullName = fullName;
	}

	
	
}
