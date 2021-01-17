package com.myclass.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private int id;
	private String email;
	private String password;
	private String fullName;
	private String address;
	private String avatar;
	private int roleId;
	private String roleDesc;
	private String roleName;

	public UserDto(int id, String email, String password, String fullName, String address, String avatar, int roleId) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.address = address;
		this.avatar = avatar;
		this.roleId = roleId;
	}

	public UserDto(int id, String email, String password, String fullName, String address, String avatar, int roleId,
			String roleDesc) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.address = address;
		this.avatar = avatar;
		this.roleId = roleId;
		this.roleDesc = roleDesc;
	}

	public UserDto(String email, String password, String roleName) {
		super();
		this.email = email;
		this.password = password;
		this.roleName = roleName;
	}
}
