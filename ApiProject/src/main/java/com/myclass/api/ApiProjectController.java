package com.myclass.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.ProjectDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.CustomUserDetails;
import com.myclass.service.ProjectService;

@RestController
@RequestMapping("api/project")
@CrossOrigin("*")
public class ApiProjectController {
	@Autowired
	ProjectService projectService;

	@GetMapping("")
	public ResponseEntity<Object> getAllUser() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String roleName = ((CustomUserDetails) principal).getRoleName();
			int userId = ((CustomUserDetails) principal).getId();
			List<ProjectDto> dtos = new ArrayList<ProjectDto>();
			if (roleName.equals("ROLE_MANAGER")) {
				dtos = projectService.findByManagerId(userId);
			} else if (roleName.equals("ROLE_ADMIN")) {
				dtos = projectService.findAll();
			}
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
