package com.ioteam.order_management_platform.review.dto;

import java.util.UUID;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ReviewRestaurantResponseDto {
	private final UUID restaurantId;
	private final String restaurantName;

	public static ReviewRestaurantResponseDto from(Restaurant restaurant) {
		return ReviewRestaurantResponseDto.builder()
			.restaurantId(restaurant.getResId())
			.restaurantName(restaurant.getResName())
			.build();
	}
}
