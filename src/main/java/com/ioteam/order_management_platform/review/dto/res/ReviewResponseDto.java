package com.ioteam.order_management_platform.review.dto.res;

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
	private final ReviewUserResponseDto user;
	private final ReviewRestaurantResponseDto restaurant;
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
			.user(ReviewUserResponseDto.from(review.getUser()))
			.restaurant(ReviewRestaurantResponseDto.from(review.getRestaurant()))
			.reviewScore(review.getReviewScore())
			.reviewContent(review.getReviewContent())
			.reviewImageUrl(review.getReviewImageUrl())
			.isPublic(review.getIsPublic())
			.createdAt(review.getCreatedAt())
			.build();
	}

	public static ReviewResponseDto from(Review review, ReviewUserResponseDto reviewUser,
		ReviewRestaurantResponseDto reviewRestaurant) {
		return ReviewResponseDto
			.builder()
			.reviewId(review.getReviewId())
			.user(reviewUser)
			.restaurant(reviewRestaurant)
			.reviewScore(review.getReviewScore())
			.reviewContent(review.getReviewContent())
			.reviewImageUrl(review.getReviewImageUrl())
			.isPublic(review.getIsPublic())
			.createdAt(review.getCreatedAt())
			.build();
	}
}
