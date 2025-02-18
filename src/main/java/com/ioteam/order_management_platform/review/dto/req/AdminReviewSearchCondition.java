package com.ioteam.order_management_platform.review.dto.req;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdminReviewSearchCondition {
	private UUID userId;
	private UUID restaurantId;
	private LocalDateTime startCreatedAt;
	private LocalDateTime endCreatedAt;
	private Integer score;
	private Boolean isPublic;
	private Boolean isDeleted;
}
