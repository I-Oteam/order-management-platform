package com.ioteam.order_management_platform.review.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AdminReviewResponseDto {

	private final UUID reviewId;
	private final ReviewUserResponseDto user;
	private final int reviewScore;
	private final String reviewContent;
	private final String reviewImageUrl;
	private final UUID reviewOrderId;
	private final Boolean isPublic;
	private final LocalDateTime createdAt;
	private final UUID createdBy;
	private final LocalDateTime modifiedAt;
	private final UUID modifiedBy;
	private final LocalDateTime deletedAt;
	private final UUID deletedBy;

	public static AdminReviewResponseDto from(Review review, User user) {
		return AdminReviewResponseDto
			.builder()
			.reviewId(review.getReviewId())
			.user(ReviewUserResponseDto.from(review.getUser()))
			.reviewScore(review.getReviewScore())
			.reviewContent(review.getReviewContent())
			.reviewImageUrl(review.getReviewImageUrl())
			.isPublic(review.getIsPublic())
			.createdAt(review.getCreatedAt())
			.createdBy(review.getCreatedBy())
			.modifiedAt(review.getModifiedAt())
			.modifiedBy(review.getModifiedBy())
			.deletedAt(review.getDeletedAt())
			.deletedBy(review.getDeletedBy())
			.build();
	}

	public static AdminReviewResponseDto from(Review review) {
		return AdminReviewResponseDto
			.builder()
			.reviewId(review.getReviewId())
			.user(ReviewUserResponseDto.from(review.getUser()))
			.reviewScore(review.getReviewScore())
			.reviewContent(review.getReviewContent())
			.reviewImageUrl(review.getReviewImageUrl())
			.isPublic(review.getIsPublic())
			.createdAt(review.getCreatedAt())
			.createdBy(review.getCreatedBy())
			.modifiedAt(review.getModifiedAt())
			.modifiedBy(review.getModifiedBy())
			.deletedAt(review.getDeletedAt())
			.deletedBy(review.getDeletedBy())
			.build();
	}
}
