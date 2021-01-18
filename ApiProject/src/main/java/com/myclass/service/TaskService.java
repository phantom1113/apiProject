package com.myclass.service;

import java.util.HashMap;
import java.util.List;

import com.myclass.dto.TaskDto;
import com.myclass.dto.UserDetailDto;

public interface TaskService {
	public List<TaskDto> getAll();
	
	public List<TaskDto> getAllOfManager(int id);
	
	public List<TaskDto> getAllOfMember(int id);

	public int add(TaskDto user);
	
	public int deleteById(int id);
	
	public int edit(int id, TaskDto dto);
	
	public TaskDto findById(int id);
	
	public int updateStatusTask(int statusId, int taskId);

	public HashMap<String, Integer> countStatusTaskInProject(int project);
	
	public HashMap<String, Integer> countStatusTaskInUser(int userId);
	
	public HashMap<String, Integer> countStatusTaskInManager(int managerId);
	
	public HashMap<String, Integer> countStatusTask();
	
	public List<UserDetailDto> getAllTaskInProject(int id);
}
