package com.ioteam.order_management_platform.review.dto.res;

import java.util.UUID;

import com.ioteam.order_management_platform.user.entity.User;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewUserResponseDto {
	private final UUID userId;
	private final String username;
	private final String nickname;

	@QueryProjection
	@Builder
	public ReviewUserResponseDto(UUID userId, String username, String nickname) {
		this.userId = userId;
		this.username = username;
		this.nickname = nickname;
	}

	public static ReviewUserResponseDto from(User user) {
		return ReviewUserResponseDto.builder()
			.userId(user.getUserId())
			.username(user.getUsername())
			.nickname(user.getNickname())
			.build();
	}
}
