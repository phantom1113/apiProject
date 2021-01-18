package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myclass.dto.ProjectDto;
import com.myclass.entity.Project;
import com.myclass.repository.ProjectRepository;
import com.myclass.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
	private ProjectRepository projectRepository;

	public ProjectServiceImpl(ProjectRepository projectRepository) {
		super();
		this.projectRepository = projectRepository;
	}

	@Override
	public List<ProjectDto> findAll() {
		try {
			List<ProjectDto> dtos = new ArrayList<ProjectDto>();
			List<Project> projects = projectRepository.findAll();
			if (!projects.isEmpty()) {
				for (Project project : projects) {
					ProjectDto dto = new ProjectDto(project.getId(), project.getName(), project.getStartDate(),
							project.getEndDate(), project.getUserId());
					dtos.add(dto);
				}
				return dtos;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int add(ProjectDto dto) {
		try {
			Project project = new Project();
			project.setName(dto.getName());
			project.setStartDate(dto.getStartDate());
			project.setEndDate(dto.getEndDate());
			project.setUserId(dto.getUserId());
			projectRepository.save(project);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public ProjectDto findById(int id) {
		try {
			Project project = projectRepository.findById(id).get();
			if (project != null) {
				return new ProjectDto(project.getId(), project.getName(), project.getStartDate(), project.getEndDate(),
						project.getUserId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int deleteById(int id) {
		try {
			projectRepository.deleteById(id);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int edit(int id, ProjectDto dto) {
		try {
			Project project = projectRepository.findById(id).get();
			project.setId(id);
			project.setName(dto.getName());
			project.setStartDate(dto.getStartDate());
			project.setEndDate(dto.getEndDate());
			projectRepository.save(project);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//Tìm tất cả project của manager đang quản lý
	@Override
	public List<ProjectDto> findByManagerId(int userId) {
		try {
			List<ProjectDto> dtos = new ArrayList<ProjectDto>();
			List<Project> projects = projectRepository.findByUserId(userId);
			if (!projects.isEmpty()) {
				for (Project project : projects) {
					ProjectDto dto = new ProjectDto(project.getId(), project.getName(), project.getStartDate(),
							project.getEndDate(), project.getUserId());
					dtos.add(dto);
				}
				return dtos;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
