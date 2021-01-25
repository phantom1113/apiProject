package com.myclass.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.myclass.dto.ProjectDto;
import com.myclass.entity.Project;

public interface ProjectService {
	public List<ProjectDto> findAll();
	
	public int add(ProjectDto dto);
	
	public ProjectDto findById(int id);
	
	public int deleteById(int id);
	
	public int edit(int id, ProjectDto dto);
	
	public List<ProjectDto> findByManagerId(int id);
	
	public Page<Project> findAllProject(int pageIndex, int pageSize);
	
	public Page<Project> findAllProjectOfManager(int pageIndex, int pageSize, int id);
}
