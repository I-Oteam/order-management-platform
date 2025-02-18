package com.ioteam.order_management_platform.review.dto;

import java.util.UUID;

import com.ioteam.order_management_platform.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ReviewUserResponseDto { // todo. 리뷰 응답에서 담을 유저 정보이므로, 리뷰 패키지 내에 작성해봄
	private final UUID userId;
	private final String username;
	private final String nickname;

	public static ReviewUserResponseDto from(User user) {
		return ReviewUserResponseDto.builder()
			.userId(user.getUserId())
			.username(user.getUsername())
			.nickname(user.getNickname())
			.build();
	}
}
