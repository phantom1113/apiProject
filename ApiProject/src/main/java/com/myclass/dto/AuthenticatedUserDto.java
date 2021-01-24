package com.myclass.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDto {
	String email;
	String fullName;
	String avatar;
	String token;

	public AuthenticatedUserDto(String email, String fullName, String avatar) {
		super();
		this.email = email;
		this.fullName = fullName;
		this.avatar = avatar;
	}
}
