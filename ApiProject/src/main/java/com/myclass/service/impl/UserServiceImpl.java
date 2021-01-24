package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.AuthenticatedUserDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.UserEditDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;
import com.myclass.service.AmazonClientService;
import com.myclass.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private AmazonClientService amazonClientService;

	public UserServiceImpl(UserRepository userRepository, AmazonClientService amazonClientService) {
		this.amazonClientService = amazonClientService;
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
					dto.setRoleId(user.getRoleId());
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
	public int add(UserDto dto, MultipartFile file) {
		try {
			User user = new User();
			user.setEmail(dto.getEmail());
			user.setFullName(dto.getFullName());
			user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
			user.setAvatar(amazonClientService.uploadFile(file));
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
	public int edit(UserEditDto dto, MultipartFile file) {
		try {
			User user = userRepository.findById(dto.getId()).get();
			user.setEmail(dto.getEmail());
			user.setFullName(dto.getFullName());
			if(dto.getPassword() != null) {
				System.out.println(dto.getPassword());
				user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
			}
			amazonClientService.deleteFile(user.getAvatar());
			user.setAvatar(amazonClientService.uploadFile(file));
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

	@Override
	public UserDto getUserByFullname(String fullname) {
		try {
			User user = userRepository.findUserByFullname(fullname);
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
	public Page<UserDto> getAllUser(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return userRepository.findAllUserRole(pageable);
	}

	@Override
	public UserDto findByEmail(String email) {
		try {
			return userRepository.findByEmail(email);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public AuthenticatedUserDto findByEmailForAuthentication(String email) {
		try {
			UserDto user = userRepository.findByEmail(email);
			if (user != null) {
				AuthenticatedUserDto dto = new AuthenticatedUserDto(user.getEmail(), user.getFullName(),
						user.getAvatar());
				return dto;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
