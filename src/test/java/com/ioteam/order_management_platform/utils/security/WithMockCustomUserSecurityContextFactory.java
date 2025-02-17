package com.ioteam.order_management_platform.utils.security;

import java.util.Collections;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUserPrincipal) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		User user = User.builder()
			.userId(UUID.fromString(customUserPrincipal.userId()))
			.username(customUserPrincipal.username())
			.password("password")
			.role(UserRoleEnum.valueOf(customUserPrincipal.role()))
			.build();

		UserDetailsImpl userDetails = new UserDetailsImpl(user);

		var authorities = Collections.
			singletonList(new SimpleGrantedAuthority(user.getRole().getAuthority()));

		Authentication auth =
			UsernamePasswordAuthenticationToken.authenticated(userDetails, "password", authorities);
		context.setAuthentication(auth);
		return context;
	}
}
