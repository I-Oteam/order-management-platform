package com.ioteam.order_management_platform.menu.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ioteam.order_management_platform.menu.dto.res.MenuResponseDto;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

public interface MenuCustomRepository {
	Page<MenuResponseDto> findMenusByResIdAndRole(UUID resId, UserDetailsImpl userDetails, Pageable pageable);
}
