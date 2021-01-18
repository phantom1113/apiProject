package com.myclass.service;

import java.util.List;

import com.myclass.dto.ProjectDto;

public interface ProjectService {
	public List<ProjectDto> findAll();
	
	public int add(ProjectDto dto);
	
	public ProjectDto findById(int id);
	
	public int deleteById(int id);
	
	public int edit(int id, ProjectDto dto);
	
	public List<ProjectDto> findByManagerId(int id);
}
