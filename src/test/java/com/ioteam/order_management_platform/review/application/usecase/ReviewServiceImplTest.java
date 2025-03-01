package com.ioteam.order_management_platform.review.application.usecase;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import com.ioteam.order_management_platform.global.IntegrationBaseTest;
import com.ioteam.order_management_platform.restaurant.event.RestaurantScoreEventListener;
import com.ioteam.order_management_platform.restaurant.event.UpdateRestaurantScoreEvent;
import com.ioteam.order_management_platform.review.dto.req.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewResponseDto;
import com.ioteam.order_management_platform.review.service.ReviewService;

import jakarta.annotation.Resource;

@RecordApplicationEvents
class ReviewServiceImplTest extends IntegrationBaseTest {

	@Autowired
	private ReviewService reviewService;

	@Resource
	ApplicationEvents applicationEvents;

	@MockitoBean
	RestaurantScoreEventListener restaurantScoreEventListener;

	@DisplayName("리뷰 생성_평점 이벤트 동작 테스트_권장")
	@Test
	void createReviewTriggerScoreUpdateEvent() {
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
		ReviewResponseDto reviewResponse = reviewService.createReview(userId, request);

		// then
		// 이벤트 발행이 잘 되었는지 확인
		assertThat(applicationEvents.stream(UpdateRestaurantScoreEvent.class).count()).isEqualTo(1);

		// 이벤트 수신이 잘 되었는지 검증
		ArgumentCaptor<UpdateRestaurantScoreEvent> captor = ArgumentCaptor.forClass(UpdateRestaurantScoreEvent.class);
		Mockito.verify(restaurantScoreEventListener).updateRestaurantScore(captor.capture());
	}

}