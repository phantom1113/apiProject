package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myclass.dto.RoleDto;
import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;
import com.myclass.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	private RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleDto> findAll() {
		try {
			List<RoleDto> dtos = new ArrayList<RoleDto>();
			List<Role> roles = roleRepository.findAll();
			if (!roles.isEmpty()) {
				for (Role role : roles) {
					RoleDto dto = new RoleDto(role.getId(), role.getName(), role.getDesc());
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
	public int add(RoleDto dto) {
		try {
			Role role = new Role();
			role.setName(dto.getName());
			role.setDesc(dto.getDesc());
			roleRepository.save(role);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public RoleDto findById(int id) {
		try {
			Role role = roleRepository.findById(id).get();
			if (role != null) {
				RoleDto dto = new RoleDto(role.getId(), role.getName(), role.getDesc());
				return dto;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int deleteById(int id) {
		try {
			if (findById(id) != null) {
				roleRepository.deleteById(id);
				return 0;
			}
			return -1;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int edit(int id, RoleDto dto) {
		try {
			Role role = new Role(id, dto.getName(), dto.getDesc());
			roleRepository.save(role);
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}

}
