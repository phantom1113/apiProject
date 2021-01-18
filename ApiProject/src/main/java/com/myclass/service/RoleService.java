package com.myclass.service;

import java.util.List;

import com.myclass.dto.RoleDto;

public interface RoleService {
	public List<RoleDto> findAll();
	
	public int add(RoleDto dto);
	
	public RoleDto findById(int id);
	
	public int deleteById(int id);
	
	public int edit(int id, RoleDto dto);
}
