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

import com.myclass.dto.TaskDto;
import com.myclass.entity.CustomUserDetails;
import com.myclass.service.TaskService;

@RestController
@RequestMapping("api/task")
@CrossOrigin("*")
public class ApiTaskController {

	@Autowired
	TaskService taskService;

	@GetMapping("")
	public ResponseEntity<Object> getAllTask() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String roleName = ((CustomUserDetails) principal).getRoleName();
			int userId = ((CustomUserDetails) principal).getId();
			List<TaskDto> dtos = new ArrayList<TaskDto>();
			if (roleName.equals("ROLE_ADMIN")) {
				dtos = taskService.getAllOfManager(userId);
			} else if (roleName.equals("ROLE_USER")) {
				dtos = taskService.getAllOfMember(userId);
			}
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("")
	public ResponseEntity<Object> add(@RequestBody TaskDto dto) {
		try {
			taskService.add(dto);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("")
	public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody TaskDto dto) {
		try {
			if (taskService.findById(id) == null) {
				return new ResponseEntity<Object>("Không tìm thấy dữ liệu!", HttpStatus.BAD_REQUEST);
			}
			taskService.edit(id, dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("")
	public ResponseEntity<Object> delete(@PathVariable("id") int id) {
		try {
			taskService.deleteById(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable("id") int id){
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int userId = ((CustomUserDetails) principal).getId();
			String roleName = ((CustomUserDetails) principal).getRoleName();
			TaskDto dto = taskService.findById(id);
			if(roleName.equals("ROLE_MANAGER") && dto.getManagerId() == userId) {
				return new ResponseEntity<Object>(taskService.findById(id),HttpStatus.OK);
			}
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("{id}/updatestatus")
	public ResponseEntity<Object> updateStatus(@PathVariable("id") int id, @RequestBody TaskDto dto) {
		try {
			
			taskService.updateStatusTask(id, dto.getStatusId());
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("countstatustasks")
	public ResponseEntity<Object> countStatusTask() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int userId = ((CustomUserDetails) principal).getId();
			String roleName = ((CustomUserDetails) principal).getRoleName();
			if (roleName.equals("ROLE_ADMIN")) {
				return new ResponseEntity<Object>(taskService.countStatusTaskInManager(userId), HttpStatus.OK);
			} else if (roleName.equals("ROLE_USER")) {
				return new ResponseEntity<Object>(taskService.countStatusTaskInUser(userId), HttpStatus.OK);
			}
			taskService.countStatusTask();
			return new ResponseEntity<Object>(taskService.countStatusTask(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
