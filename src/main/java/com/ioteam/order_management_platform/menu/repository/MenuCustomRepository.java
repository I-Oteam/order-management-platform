package com.ioteam.order_management_platform.menu.repository;

import java.util.List;
import java.util.UUID;

import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

public interface MenuCustomRepository {
	List<Menu> findMenusByResIdAndRole(UUID resId, UserDetailsImpl userDetails);
}
