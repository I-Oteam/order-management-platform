package com.ioteam.order_management_platform.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AdminUserResponseDto {

	private final UUID userId;
	private final String username;
	private final String password;
	private final String nickname;
	private final String email;
	private final UserRoleEnum role;
	private final LocalDateTime createdAt;
	private final UUID createdBy;
	private final LocalDateTime modifiedAt;
	private final UUID modifiedBy;
	private final LocalDateTime deletedAt;
	private final UUID deletedBy;

	public static AdminUserResponseDto from(User user) {
		return AdminUserResponseDto
			.builder()
			.userId(user.getUserId())
			.username(user.getUsername())
			.password(user.getPassword())
			.nickname(user.getNickname())
			.email(user.getEmail())
			.role(user.getRole())
			.createdAt(user.getCreatedAt())
			.createdBy(user.getCreatedBy())
			.modifiedAt(user.getModifiedAt())
			.modifiedBy(user.getModifiedBy())
			.deletedAt(user.getDeletedAt())
			.deletedBy(user.getDeletedBy())
			.build();
	}
}
