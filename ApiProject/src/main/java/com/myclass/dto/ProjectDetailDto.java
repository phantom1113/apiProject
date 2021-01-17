package com.myclass.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDetailDto {
	int id;
	List<UserDetailDto> users;
	public ProjectDetailDto(int id, List<UserDetailDto> users) {
		super();
		this.id = id;
		this.users = users;
	}
	
}
