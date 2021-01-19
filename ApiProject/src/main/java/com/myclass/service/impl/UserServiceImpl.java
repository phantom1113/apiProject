package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.myclass.dto.UserDto;
import com.myclass.dto.UserEditDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;
import com.myclass.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public List<UserDto> getAll() {
		try {
			List<UserDto> dtos = new ArrayList<UserDto>();
			List<User> users = userRepository.findAll();
			if (!users.isEmpty()) {
				for (User user : users) {
					UserDto dto = new UserDto();
					dto.setEmail(user.getEmail());
					dto.setFullName(user.getFullName());
					dto.setId(user.getId());
					dto.setPassword(user.getPassword());
					dto.setAvatar(user.getAvatar());
					dto.setRoleId(user.getId());
					dto.setRoleDesc(user.getRole().getDesc());
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
	public int add(UserDto dto) {
		try {
			User user = new User();
			user.setEmail(dto.getEmail());
			user.setFullName(dto.getFullName());
			user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
			user.setAvatar(dto.getAvatar());
			user.setRoleId(dto.getRoleId());
			userRepository.save(user);
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int deleteById(int id) {
		try {
			if (findById(id) != null) {
				userRepository.deleteById(id);
				return 0;
			}
			return -1;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int edit(UserEditDto dto) {
		try {
			User user = userRepository.findById(dto.getId()).get();
			user.setEmail(dto.getEmail());
			user.setFullName(dto.getFullName());
			if(!dto.getPassword().equals("")) {
				user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
			}
			user.setAvatar(dto.getAvatar());
			user.setRoleId(dto.getRoleId());
			userRepository.save(user);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public UserDto findById(int id) {
		try {
			User user = userRepository.findById(id).get();
			if (user != null) {
				UserDto dto = new UserDto();
				dto.setId(user.getId());
				dto.setEmail(user.getEmail());
				dto.setPassword(user.getPassword());
				dto.setFullName(user.getFullName());
				dto.setAvatar(user.getAvatar());
				dto.setRoleId(user.getRoleId());
				return dto;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<UserDto> findUserRoleName(String roleName) {
		try {
			return userRepository.findByRole(roleName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserEditDto findByIdForEdit(int id) {
		try {
			User user = userRepository.findById(id).get();
			if (user != null) {
				UserEditDto dto = new UserEditDto();
				dto.setId(user.getId());
				dto.setEmail(user.getEmail());
				dto.setPassword(user.getPassword());
				dto.setFullName(user.getFullName());
				dto.setAvatar(user.getAvatar());
				dto.setRoleId(user.getRoleId());
				return dto;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
