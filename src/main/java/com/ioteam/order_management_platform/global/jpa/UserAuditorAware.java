package com.ioteam.order_management_platform.global.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

public class UserAuditorAware implements AuditorAware<UUID> {

	@Override
	public Optional<UUID> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication || !authentication.isAuthenticated()) {
			return null;
		}
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
		return Optional.of(userDetails.getUserId());
	}

}