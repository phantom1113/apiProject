package com.myclass.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.StatusDto;
import com.myclass.service.StatusService;

@RestController
@RequestMapping("api/status")
@CrossOrigin("*")
public class ApiStatusController {
	
	@Autowired
	StatusService statusService;
	
	@GetMapping("")
	public ResponseEntity<Object> getAll(){
		try {

			List<StatusDto> dtos = new ArrayList<StatusDto>();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
