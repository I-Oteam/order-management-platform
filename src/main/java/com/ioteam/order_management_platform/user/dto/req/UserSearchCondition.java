package com.ioteam.order_management_platform.user.dto.req;

import java.time.LocalDateTime;

import com.ioteam.order_management_platform.user.entity.UserRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSearchCondition {
	private LocalDateTime startCreatedAt;
	private LocalDateTime endCreatedAt;
	private UserRoleEnum role;
	private Boolean isDeleted;
}
