package com.ioteam.order_management_platform.user.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
	UUID userId;
	String username;
	String email;
	String nickname;
	LocalDateTime createDate;
	LocalDateTime updateDate;

	public static UserInfoResponseDto from(User user) {
		return UserInfoResponseDto
			.builder()
			.userId(user.getUserId())
			.username(user.getUsername())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.createDate(user.getCreatedAt())
			.updateDate(user.getModifiedAt())
			.build();
	}
}