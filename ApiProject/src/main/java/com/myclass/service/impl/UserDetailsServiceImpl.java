package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myclass.constants.MessageConstants;
import com.myclass.dto.UserDto;
import com.myclass.entity.CustomUserDetail;
import com.myclass.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//Phương thức kiểm tra email or username
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		//Danh sách chứa tên quyền của người dùng
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		UserDto dto = userRepository.findByEmail(email);
		if(dto == null) {
			throw new UsernameNotFoundException(MessageConstants.MESSAGE_LOGIN_EMAIL);
		}
		
		
		authorities.add(new SimpleGrantedAuthority(dto.getRoleName()));
		
		//Lấy thông tin đăng nhập
		CustomUserDetail customUserDetail = new CustomUserDetail(email, dto.getPassword(), authorities);
		customUserDetail.setAvatar(dto.getAvatar());
		customUserDetail.setId(dto.getId());
		customUserDetail.setFullName(dto.getFullName());
		customUserDetail.setRoleName(dto.getRoleName());
		return customUserDetail;
	}

}
