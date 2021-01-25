package com.myclass.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.ProjectDto;
import com.myclass.entity.CustomUserDetails;
import com.myclass.service.ProjectService;
import com.myclass.service.TaskService;

@RestController
@RequestMapping("api/project")
@CrossOrigin("*")
public class ApiProjectController {
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TaskService taskService;

	@GetMapping("")
	public ResponseEntity<Object> getAll() {
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
			else if (roleName.equals("ROLE_USER")) {
				return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Object> add(@RequestBody ProjectDto dto){
		try {
			projectService.add(dto);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody ProjectDto dto){
		try {
			if(projectService.findById(id) == null) {
				return new ResponseEntity<Object>("Không tìm thấy dữ liệu!", HttpStatus.BAD_REQUEST);
			}
			projectService.edit(id, dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") int id, @RequestBody ProjectDto dto){
		try {
			projectService.deleteById(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("{id}/alltasks")
	public ResponseEntity<Object> getAllTaskOfProject(@PathVariable("id") int id){
		try {
			if(projectService.findById(id) == null) {
				return new ResponseEntity<Object>("Dự án không tồn tại!", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Object>(taskService.getAllTaskInProject(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("{id}/statistic")
	public ResponseEntity<Object> getStatisticalNumbers(@PathVariable("id") int id){
		try {
			if(projectService.findById(id) == null) {
				return new ResponseEntity<Object>("Dự án không tồn tại!", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Object>(taskService.countStatusTaskInProject(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
