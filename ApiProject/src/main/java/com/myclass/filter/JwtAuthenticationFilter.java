package com.myclass.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// B1: Lấy token từ requenst
		String authorization = request.getHeader("Authorization");
		if ( authorization != null && !authorization.isEmpty()) {
			// B2: Giải ngược token => Lấy email đã lưu vào token ở bước đăng nhập
			String email = Jwts.parser().setSigningKey("chuoi_bi_mat").parseClaimsJws(authorization).getBody().getSubject();
			// B3: Truy vấn DB lấy thông tin user(Sử dụng email vừa lấy từ token)
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			// B4: Lưu thông tin user vào securityContext(để phân quyền)
			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
		}
		chain.doFilter(request, response);
	}
}
