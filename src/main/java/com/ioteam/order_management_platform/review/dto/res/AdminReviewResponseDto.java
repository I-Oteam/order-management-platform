package com.ioteam.order_management_platform.review.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdminReviewResponseDto {

	private final UUID reviewId;
	private final ReviewUserResponseDto user;
	private final ReviewRestaurantResponseDto restaurant;
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

	@QueryProjection
	@Builder
	public AdminReviewResponseDto(UUID reviewId, ReviewUserResponseDto user, ReviewRestaurantResponseDto restaurant,
		int reviewScore, String reviewContent, String reviewImageUrl, UUID reviewOrderId, Boolean isPublic,
		LocalDateTime createdAt, UUID createdBy, LocalDateTime modifiedAt, UUID modifiedBy, LocalDateTime deletedAt,
		UUID deletedBy) {
		this.reviewId = reviewId;
		this.user = user;
		this.restaurant = restaurant;
		this.reviewScore = reviewScore;
		this.reviewContent = reviewContent;
		this.reviewImageUrl = reviewImageUrl;
		this.reviewOrderId = reviewOrderId;
		this.isPublic = isPublic;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
		this.deletedAt = deletedAt;
		this.deletedBy = deletedBy;
	}
}
