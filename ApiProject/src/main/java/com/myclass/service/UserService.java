package com.myclass.service;

import java.util.List;

import com.myclass.dto.UserDto;
import com.myclass.dto.UserEditDto;

public interface UserService {
	public List<UserDto> getAll();

	public int add(UserDto user);
	
	public int deleteById(int id);
	
	public int edit(UserEditDto dto);
	
	public UserDto findById(int id);
	
	public UserEditDto findByIdForEdit(int id);
	
	public List<UserDto> findUserRoleName(String name);
}
