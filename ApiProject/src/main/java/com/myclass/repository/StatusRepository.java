package com.myclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myclass.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer>{

	public Status findByName(String name);
}
