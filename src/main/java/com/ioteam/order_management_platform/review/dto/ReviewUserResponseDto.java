package com.ioteam.order_management_platform.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class ReviewUserResponseDto { // todo. 리뷰 응답에서 담을 유저 정보이므로, 리뷰 패키지 내에 작성해봄
    private final UUID userId;
    private final String username;
    private final String nickname;
}
