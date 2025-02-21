package com.ioteam.order_management_platform.user.dto.res;

import java.util.UUID;

import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginResponseDto {
	private String token;
	private UUID userId;
	private String username;
	private String email;
	private UserRoleEnum role;

	public static LoginResponseDto from(User user, String token) {
		return LoginResponseDto.builder()
			.token(token)
			.userId(user.getUserId())
			.username(user.getUsername())
			.email(user.getEmail())
			.role(user.getRole())
			.build();
	}
}