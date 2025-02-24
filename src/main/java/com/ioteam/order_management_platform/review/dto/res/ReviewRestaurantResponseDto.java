package com.ioteam.order_management_platform.review.dto.res;

import java.util.UUID;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewRestaurantResponseDto {
	private final UUID restaurantId;
	private final String restaurantName;

	@QueryProjection
	@Builder
	public ReviewRestaurantResponseDto(UUID restaurantId, String restaurantName) {
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
	}

	public static ReviewRestaurantResponseDto from(Restaurant restaurant) {
		return ReviewRestaurantResponseDto.builder()
			.restaurantId(restaurant.getResId())
			.restaurantName(restaurant.getResName())
			.build();
	}
}
