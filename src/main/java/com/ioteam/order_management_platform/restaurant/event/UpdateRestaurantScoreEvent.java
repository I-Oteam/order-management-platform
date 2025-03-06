package com.ioteam.order_management_platform.restaurant.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UpdateRestaurantScoreEvent {

	private UUID restaurantId;

	private UpdateRestaurantScoreEvent(UUID restaurantId) {
		this.restaurantId = restaurantId;
	}

	public static UpdateRestaurantScoreEvent of(UUID restaurantId) {
		return new UpdateRestaurantScoreEvent(restaurantId);
	}
}
