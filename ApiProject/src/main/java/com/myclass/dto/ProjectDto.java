package com.myclass.dto;


import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDto {
	private int id;
	
	@NotEmpty(message = "Tên dự án không được bỏ trống")
	private String name;
	
	
    @NotNull(message = "Ngày bắt đầu không được bỏ trống")
	private Date startDate;
    
    @NotNull(message = "Ngày kết thúc không được bỏ trống")
	private Date endDate;
	private int userId;

	public ProjectDto(int id, String name, Date startDate, Date endDate, int userId) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userId = userId;
	}

	public ProjectDto(int id, String name, int userId) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
	}
}
