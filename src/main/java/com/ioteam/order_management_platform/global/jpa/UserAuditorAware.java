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
		if (authentication == null || !authentication.isAuthenticated()) {
			// 인증되지않으면 빈값 반환
			return Optional.empty();
		}

		if (authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
			return Optional.of(userDetails.getUserId());
		} else {
			// 인증된사용자 정보가 UserDetailsImpl이 아닌경우 빈값 반환
			return Optional.empty();
		}

	}
}
