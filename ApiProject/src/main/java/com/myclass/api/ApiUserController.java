package com.myclass.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.UserDto;
import com.myclass.dto.UserEditDto;
import com.myclass.service.UserService;

@RestController
@RequestMapping(value = "api/user")
public class ApiUserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("")
	public ResponseEntity<Object> getAllUser(){
		try {
			List<UserDto> dtos = userService.getAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getUserFindById(@PathVariable("id") int id){
		try {
			UserDto dto = userService.findById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Object> add(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "user") UserDto dto){
		try {
			if(userService.findByEmail(dto.getEmail())!= null) {
				return new ResponseEntity<Object>("Email đã tồn tại!", HttpStatus.BAD_REQUEST);
			}
			userService.add(dto, file);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> edit(@RequestPart(value = "file") MultipartFile file,@PathVariable("id") int id, @RequestPart(value = "user") UserEditDto dto) {
		try {
			if(userService.findById(id) == null) {
				return new ResponseEntity<Object>("Không tìm thấy dữ liệu!", HttpStatus.BAD_REQUEST);
			}
			userService.edit(dto, file);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") int id) {
		try {
			userService.deleteById(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);

		}
	}
	
	@GetMapping("/fullname/first/{fullname}")
	public ResponseEntity<Object> getFirstUserByFullName(@PathVariable("fullname") String fullname){
		try {
			UserDto dto = userService.getUserByFullname(fullname);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("paging/{pageIndex}/{pageSize}")
	public ResponseEntity<Object> getUserPaging(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize){
		Page<UserDto> results = userService.getAllUser(pageIndex, pageSize);
		if(pageIndex <  1 || pageSize < 1 ){
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		if(results.getSize() > 0) {
			return new ResponseEntity<Object>(results, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
