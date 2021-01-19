package com.myclass.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
	private String email;
	private String password;

	public LoginDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
}
