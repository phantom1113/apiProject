package com.myclass.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StatusDto {
	private int id;	
	private String name;
	
	public StatusDto(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
