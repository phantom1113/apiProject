package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myclass.dto.StatusDto;
import com.myclass.entity.Status;
import com.myclass.repository.StatusRepository;
import com.myclass.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService{
	private StatusRepository statusRepository;

	public StatusServiceImpl(StatusRepository statusRepository) {
		super();
		this.statusRepository = statusRepository;
	}

	@Override
	public List<StatusDto> findAll() {
		try {
			List<StatusDto> dtos = new ArrayList<StatusDto>();
			List<Status> statuses = statusRepository.findAll();
			if (!statuses.isEmpty()) {
				for (Status status : statuses) {
					StatusDto dto = new StatusDto(status.getId(),status.getName());
					dtos.add(dto);
				}
				return dtos;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
