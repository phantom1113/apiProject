package com.myclass.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.LoginDto;
import com.myclass.entity.CustomUserDetails;

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
		try {
			//Gọi hàm thực hiện đăng nhập
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = generateToken(authentication);
			return new ResponseEntity<Object>(token, HttpStatus.OK);
		}
		catch (BadCredentialsException e) {
			return new ResponseEntity<Object>("Sai tên đăng nhập hoặc mật khẩu",HttpStatus.UNAUTHORIZED);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private String generateToken(Authentication authentication) {
		// Đoạn JWT_SECRET bí mật
		final String JWT_SECRET = "secret";
		//Thời gian có hiệu lực của chuỗi jwt(10 ngày)
		final long JWT_EXPIRATION = 864000000L;
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + JWT_EXPIRATION);
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		String token = Jwts.builder()
				.setSubject(customUserDetails.getUsername())
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET)
				.compact();
		return token;
	}
}
