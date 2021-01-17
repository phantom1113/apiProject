package com.myclass.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {
	private int id;
	private String name;
	private Date startDate;	
	private Date endDate;
	private int statusId;
	private int userId;
	private int projectId;
	private int managerId;
	private String statusName;
	private String userName;
	private String projectName;
	

	public TaskDto(int id, String name, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public TaskDto(int id, String name, Date startDate, Date endDate, int statusId, int userId, int projectId, int managerId,
			String statusName, String userName, String projectName) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.statusId = statusId;
		this.userId = userId;
		this.projectId = projectId;
		this.statusName = statusName;
		this.userName = userName;
		this.managerId = managerId;
		this.projectName = projectName;
	}
		
}
