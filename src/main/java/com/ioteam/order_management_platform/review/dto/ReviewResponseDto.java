package com.ioteam.order_management_platform.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class ReviewResponseDto {

    private final UUID reviewId;
    private final ReviewUserResponseDto userResponseDto;
    private final int reviewScore;
    private final String reviewContent;
    private final String reviewImageUrl;
    private final UUID reviewOrderId;
    private final Boolean isPublic;
    private final LocalDateTime createdAt;
}
