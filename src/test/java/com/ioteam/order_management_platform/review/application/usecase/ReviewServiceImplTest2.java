package com.ioteam.order_management_platform.review.application.usecase;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;

import com.ioteam.order_management_platform.global.IntegrationBaseTest;
import com.ioteam.order_management_platform.restaurant.dto.res.RestaurantResponseDto;
import com.ioteam.order_management_platform.restaurant.service.RestaurantService;
import com.ioteam.order_management_platform.review.dto.req.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewResponseDto;
import com.ioteam.order_management_platform.review.service.ReviewService;

public class ReviewServiceImplTest2 extends IntegrationBaseTest {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	TestConfig.AsyncAspect asyncAspect;

	@DisplayName("리뷰 생성_평점 업데이트 동작 테스트")
	@Test
	void createReviewTriggerScoreUpdate() throws InterruptedException {
		// given
		var userId = UUID.fromString("d2ed72d8-090a-4efb-abe4-7acbdce120e1");
		var restaurantId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
		var orderId = UUID.fromString("d8ef5ca7-2b3c-49bb-9c6d-425c85036d11");
		var request = CreateReviewRequestDto.builder()
			.orderId(orderId)
			.restaurantId(restaurantId)
			.reviewScore(5)
			.reviewImageUrl("test.jpg")
			.reviewContent("좋아요!")
			.isPublic(true)
			.build();

		// when
		RestaurantResponseDto prevRestaurantResponse = restaurantService.searchOneRestaurant(restaurantId);
		BigDecimal prevResScore = prevRestaurantResponse.getResScore();

		asyncAspect.init();
		ReviewResponseDto reviewResponse = reviewService.createReview(userId, request);
		asyncAspect.await();

		RestaurantResponseDto restaurantResponse = restaurantService.searchOneRestaurant(restaurantId);
		BigDecimal resScore = restaurantResponse.getResScore();

		// then
		Assertions.assertEquals(BigDecimal.valueOf(3.4), prevResScore);
		Assertions.assertEquals(BigDecimal.valueOf(3.5), resScore);
	}

	@TestConfiguration
	static class TestConfig {
		@Aspect
		@TestComponent
		static class AsyncAspect {

			private CountDownLatch countDownLatch;

			public void init() {
				countDownLatch = new CountDownLatch(1);
			}

			@After("execution(* com.ioteam.order_management_platform.restaurant.event.RestaurantScoreEventListener.updateRestaurantScore(*))")
			public void afterUpdateRestaurantScore() {
				countDownLatch.countDown();
			}

			public void await() throws InterruptedException {
				countDownLatch.await();
			}
		}
	}
}
