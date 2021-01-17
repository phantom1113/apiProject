package com.myclass.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDto {
	int id;
	String fullName;
	List<TaskDto> unfilunfulfilledTask;
	List<TaskDto> completedTask;
	List<TaskDto> processingTask;
	
	public UserDetailDto(int id, String fullName, List<TaskDto> unfilunfulfilledTask, List<TaskDto> completedTask,
			List<TaskDto> processingTask) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.unfilunfulfilledTask = unfilunfulfilledTask;
		this.completedTask = completedTask;
		this.processingTask = processingTask;
	}	
}
