package com.ioteam.order_management_platform.user.dto;

import java.time.LocalDateTime;

import com.ioteam.order_management_platform.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
	String username;
	String email;
	String nickname;
	LocalDateTime createDate;
	LocalDateTime updateDate;

	public static UserInfoResponseDto from(User user) {
		return new UserInfoResponseDto(
			user.getUsername(),
			user.getEmail(),
			user.getNickname(),
			user.getCreatedAt(),
			user.getModifiedAt()
		);
	}
}