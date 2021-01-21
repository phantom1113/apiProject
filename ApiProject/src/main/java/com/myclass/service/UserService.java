package com.myclass.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.UserDto;
import com.myclass.dto.UserEditDto;

public interface UserService {
	public List<UserDto> getAll();

	public int add(UserDto user, MultipartFile file);
	
	public int deleteById(int id);
	
	public int edit(UserEditDto dto, MultipartFile file);
	
	public UserDto findById(int id);
	
	public UserEditDto findByIdForEdit(int id);
	
	public List<UserDto> findUserRoleName(String name);
	
	public UserDto getUserByFullname(String fullname);
	
	public Page<UserDto> getAllUser(int pageIndex, int pageSize);
}
