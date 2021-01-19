package com.myclass.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.LoginDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin("*")
@RequestMapping("api/auth")
public class ApiAuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("login")
	public ResponseEntity<Object> login(@RequestBody LoginDto dto) {
		final String JWT_SECRET = "chuoi_bi_mat";
		try {
			//Gọi hàm thực hiện đăng nhập
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = Jwts.builder()
							.setSubject(dto.getEmail())
							.setIssuedAt(new Date())
							.setExpiration(new Date( new Date().getTime() + 864000000L))
							.signWith(SignatureAlgorithm.HS256, JWT_SECRET)
							.compact();
			//Nếu đăng nhập thành công trả về token
			return new ResponseEntity<Object>(token, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
