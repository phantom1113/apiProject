package com.myclass.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myclass.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	public List<Project> findByUserId(int userId);
	
	@Query("SELECT p FROM Project p")
	public Page<Project> findAllProject(Pageable pageable);
	
	@Query("SELECT p FROM Project p WHERE p.userId= :id")
	public Page<Project> findAllProjectOfManager(Pageable pageable, int id);
}
