package com.ioteam.order_management_platform.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ioteam.order_management_platform.user.dto.UserSearchCondition;
import com.ioteam.order_management_platform.user.entity.User;

public interface UserRepositoryCustom {
	Page<User> searchUserByCondition(UserSearchCondition condition, Pageable pageable);
}