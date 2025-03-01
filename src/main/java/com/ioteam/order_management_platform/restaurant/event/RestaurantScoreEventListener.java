package com.ioteam.order_management_platform.restaurant.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.ioteam.order_management_platform.restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "RestaurantScore:::EventListening")
@RequiredArgsConstructor
@Component
public class RestaurantScoreEventListener {

	private final RestaurantService restaurantService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void updateRestaurantScore(UpdateRestaurantScoreEvent event) {

		log.info("레스토랑 평점 업데이트 시작");
		restaurantService.updateRestaurantScore(event.getRestaurantId());
		log.info("레스토랑 평점 업데이트 종료");
	}
}
