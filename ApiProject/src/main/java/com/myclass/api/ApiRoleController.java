package com.myclass.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.RoleDto;
import com.myclass.service.RoleService;

@RestController
@RequestMapping("api/role")
@CrossOrigin("*")
public class ApiRoleController {
	
	@Autowired
	private RoleService roleService;

	@GetMapping("")
	public ResponseEntity<Object> index() {
		try {
			List<RoleDto> dtos = roleService.findAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Lỗi server!", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> get(@PathVariable("id") int id) {
		try {
			RoleDto dto = roleService.findById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
	}
	@PostMapping("")
	public ResponseEntity<Object> add(@RequestBody RoleDto dto) {
		try {
	
			roleService.add(dto);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody RoleDto dto) {
		try {
			if(roleService.findById(id) == null)
				return new ResponseEntity<Object>("Không tìm thấy dữ liệu!", HttpStatus.BAD_REQUEST);
			
			roleService.edit(id, dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") int id) {
		try {
			roleService.deleteById(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
