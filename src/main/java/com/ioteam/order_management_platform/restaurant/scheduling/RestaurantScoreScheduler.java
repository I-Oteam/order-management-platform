package com.ioteam.order_management_platform.restaurant.scheduling;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.restaurant.repository.RestaurantScoreRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "RestaurantScore:::Scheduling")
@Component
@ConditionalOnProperty(name = "scheduled.enabled", havingValue = "true")
@RequiredArgsConstructor
public class RestaurantScoreScheduler {

	private final RestaurantScoreRepository restaurantScoreRepository;

	@Scheduled(cron = "0 * * * * *")
	@Transactional
	public void run() {
		log.info("레스토랑 평점 업데이트 시작");
		restaurantScoreRepository.bulkUpdateRestaurantScore();
		log.info("레스토랑 평점 업데이트 종료");
	}
}
