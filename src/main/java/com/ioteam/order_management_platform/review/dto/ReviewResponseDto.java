package com.ioteam.order_management_platform.review.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.review.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

	public static ReviewResponseDto from(Review review) {
		return ReviewResponseDto
			.builder()
			.reviewId(review.getReviewId())
			//.userResponseDto(this.user.toReviewUserResponseDto())
			.reviewScore(review.getReviewScore())
			.reviewContent(review.getReviewContent())
			.reviewImageUrl(review.getReviewImageUrl())
			.isPublic(review.getIsPublic())
			.createdAt(review.getCreatedAt())
			.build();
	}
}
