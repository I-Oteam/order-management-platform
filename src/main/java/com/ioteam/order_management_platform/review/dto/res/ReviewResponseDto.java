package com.ioteam.order_management_platform.review.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.review.entity.Review;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

	private final UUID reviewId;
	private final ReviewUserResponseDto user;
	private final ReviewRestaurantResponseDto restaurant;
	private final int reviewScore;
	private final String reviewContent;
	private final String reviewImageUrl;
	private final UUID reviewOrderId;
	private final Boolean isPublic;
	private final LocalDateTime createdAt;

	@QueryProjection
	@Builder
	public ReviewResponseDto(UUID reviewId, ReviewUserResponseDto user, ReviewRestaurantResponseDto restaurant,
		int reviewScore, String reviewContent, String reviewImageUrl, UUID reviewOrderId, Boolean isPublic,
		LocalDateTime createdAt) {
		this.reviewId = reviewId;
		this.user = user;
		this.restaurant = restaurant;
		this.reviewScore = reviewScore;
		this.reviewContent = reviewContent;
		this.reviewImageUrl = reviewImageUrl;
		this.reviewOrderId = reviewOrderId;
		this.isPublic = isPublic;
		this.createdAt = createdAt;
	}

	public static ReviewResponseDto from(Review review) {
		return ReviewResponseDto
			.builder()
			.reviewId(review.getReviewId())
			.user(ReviewUserResponseDto.from(review.getUser()))
			.restaurant(ReviewRestaurantResponseDto.from(review.getRestaurant()))
			.reviewScore(review.getReviewScore())
			.reviewContent(review.getReviewContent())
			.reviewImageUrl(review.getReviewImageUrl())
			.isPublic(review.getIsPublic())
			.createdAt(review.getCreatedAt())
			.build();
	}

}
