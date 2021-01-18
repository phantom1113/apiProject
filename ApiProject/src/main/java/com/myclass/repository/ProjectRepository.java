package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myclass.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	public List<Project> findByUserId(int userId);
}
