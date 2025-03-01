package com.ioteam.order_management_platform.user.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ioteam.order_management_platform.user.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;

	public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws
		ServletException,
		IOException {

		String tokenValue = jwtUtil.getJwtFromHeader(req);
		if (StringUtils.hasText(tokenValue) && jwtUtil.validateToken(tokenValue)) {
			try {
				Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
				String username = info.getSubject();

				if (username != null) {
					setAuthentication(username);
				} else {
					log.error("JWT 토큰에서 사용자 이름을 찾을 수 없습니다.");
				}
			} catch (Exception e) {
				log.error("JWT 인증 중 오류 발생: " + e.getMessage());
			}
		}
		filterChain.doFilter(req, res);
	}

	// 인증 처리
	public void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String username) {
		UserDetailsImpl userDetails = (UserDetailsImpl)userDetailsService.loadUserByUsername(username);
		log.info("Set Authentication ::::: Role - {}", userDetails.getRole().name());
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}