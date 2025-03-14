package com.ioteam.order_management_platform.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteam.order_management_platform.review.dto.req.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.req.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.utils.security.WithMockCustomUser;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class ReviewIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e3", role = "MANAGER")
	@DisplayName("매니저_리뷰 리스트 조회 성공 200")
	@Test
	void searchReviewAdmin_200() throws Exception {
		// given
		LocalDateTime startCreatedAt = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
		LocalDateTime endCreatedAt = LocalDateTime.now().plus(1, ChronoUnit.HOURS);
		Integer score = 4;
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("startCreatedAt", startCreatedAt.toString());
		param.add("endCreatedAt", endCreatedAt.toString());
		param.add("score", score.toString());

		// when, then
		mockMvc.perform(
				get("/api/reviews/admin")
					.params(param)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.content[0].reviewScore")
				.value(4))
			.andExpect(jsonPath("$.result.totalElements")
				.value(5))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("고객_리뷰 리스트 조회 실패 403")
	@Test
	void searchReviewAdmin_403() throws Exception {
		// given
		LocalDateTime startCreatedAt = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
		LocalDateTime endCreatedAt = LocalDateTime.now().plus(1, ChronoUnit.HOURS);
		Integer score = 4;
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("startCreatedAt", startCreatedAt.toString());
		param.add("endCreatedAt", endCreatedAt.toString());
		param.add("score", score.toString());

		// when, then
		mockMvc.perform(
				get("/api/reviews/admin")
					.params(param)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isForbidden())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message")
				.value("권한이 없는 요청입니다."))
			.andExpect(jsonPath("$.errorCode")
				.value("E_UNAUTH"))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("고객_본인 리뷰 리스트 조회 성공 200")
	@Test
	void searchReviewsByUser_200() throws Exception {
		// given
		UUID userId = UUID.fromString("d2ed72d8-090a-4efb-abe4-7acbdce120e1");

		// when, then
		mockMvc.perform(
				get("/api/reviews/users/{userId}", userId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.content[0].reviewScore")
				.value(5))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("고객_타인 리뷰 리스트 조회 실패 403")
	@Test
	void searchReviewsByUser_403() throws Exception {
		// given
		UUID userId = UUID.fromString("d2ed72d8-090a-4efb-abe4-7acbdce120e2");

		// when, then
		mockMvc.perform(
				get("/api/reviews/users/{userId}", userId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isForbidden())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message")
				.value("권한이 없는 요청입니다."))
			.andExpect(jsonPath("$.errorCode")
				.value("E_UNAUTH"))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e2", role = "OWNER")
	@DisplayName("오너_가게 리뷰 리스트 조회 성공 200")
	@Test
	void searchReviewsByRestaurant_200() throws Exception {
		// given
		UUID resId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

		// when, then
		mockMvc.perform(
				get("/api/reviews/restaurants/{resId}", resId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.content[0].reviewScore")
				.value(5))
			.andDo(print());
	}

	@DisplayName("비인증_가게 리뷰 리스트 조회 실패 403")
	@Test
	void searchReviewsByRestaurant_403() throws Exception {
		// given
		UUID resId = UUID.fromString("768f5a3b-e6d8-46d6-f953-b9c425ab1cf1");

		// when, then
		mockMvc.perform(
				get("/api/reviews/restaurants/{resId}", resId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isForbidden())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e2", role = "OWNER")
	@DisplayName("오너_소유 가게 비공개 리뷰 단건 조회 성공 200")
	@Test
	void owner_getReview_200() throws Exception {
		// given
		String reviewId = "1c114e8f-ccd0-42b8-a7fa-67fee61d4d18";

		// when, then
		mockMvc.perform(get("/api/reviews/{reviewId}", reviewId)
				.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.reviewScore")
				.value(5))
			.andExpect(jsonPath("$.result.reviewContent")
				.value("test"))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e5", role = "OWNER")
	@DisplayName("오너_다른 가게 비공개 리뷰 단건 조회 실패 403")
	@Test
	void owner_getReview_403() throws Exception {
		// given
		String reviewId = "1c114e8f-ccd0-42b8-a7fa-67fee61d4d18";

		// when, then
		mockMvc.perform(get("/api/reviews/{reviewId}", reviewId)
				.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isForbidden())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("고객_리뷰 생성 성공 201")
	@Test
	void createReview_201() throws Exception {
		// given
		CreateReviewRequestDto request = CreateReviewRequestDto.builder()
			.orderId(UUID.fromString("d8ef5ca7-2b3c-49bb-9c6d-425c85036d11"))
			.restaurantId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
			.reviewScore(5)
			.reviewContent("test")
			.reviewImageUrl("test")
			.isPublic(true)
			.build();

		// when, then
		mockMvc.perform(post("/api/reviews")
				.header("Authorization", "Bearer {ACCESS_TOKEN}")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.reviewScore")
				.value(5))
			.andExpect(jsonPath("$.result.reviewContent")
				.value("test"))
			.andExpect(jsonPath("$.result.reviewImageUrl")
				.value("test"))
			.andExpect(jsonPath("$.result.isPublic")
				.value(true))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("고객_리뷰 생성 실패_완료되지 않은 주문 422")
	@Test
	void createReview_422() throws Exception {
		// given
		CreateReviewRequestDto request = CreateReviewRequestDto.builder()
			.orderId(UUID.fromString("d8ef5ca7-2b3c-49bb-9c6d-425c85036d12"))
			.restaurantId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
			.reviewScore(5)
			.reviewContent("test")
			.reviewImageUrl("test")
			.isPublic(true)
			.build();

		// when, then
		mockMvc.perform(post("/api/reviews")
				.header("Authorization", "Bearer {ACCESS_TOKEN}")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnprocessableEntity())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("유저_리뷰 삭제 성공 200")
	@Test
	void deleteReview_200() throws Exception {
		// given
		UUID reviewId = UUID.fromString("1c114e8f-ccd0-42b8-a7fa-67fee61d4d13");

		// when, then
		mockMvc.perform(delete("/api/reviews/{reviewId}", reviewId)
				.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("유저_리뷰 삭제 실패_존재하지 않는 리뷰아이디 404")
	@Test
	void deleteReview_404() throws Exception {
		// given
		UUID reviewId = UUID.fromString("1c114e8f-ccd0-42b8-a7fa-97fee61d4d15");

		// when, then
		mockMvc.perform(delete("/api/reviews/{reviewId}", reviewId)
				.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("고객_본인 리뷰 수정 성공 200")
	@Test
	void modifyReview_200() throws Exception {
		// given
		UUID reviewId = UUID.fromString("1c114e8f-ccd0-42b8-a7fa-67fee61d4d13");
		ModifyReviewRequestDto request = ModifyReviewRequestDto.builder()
			.reviewScore(3)
			.reviewContent("modifyTest")
			.reviewImageUrl("modifyTest")
			.isPublic(false)
			.build();

		// when, then
		mockMvc.perform(patch("/api/reviews/{reviewId}", reviewId)
				.header("Authorization", "Bearer {ACCESS_TOKEN}")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.reviewScore")
				.value(3))
			.andExpect(jsonPath("$.result.reviewContent")
				.value("modifyTest"))
			.andExpect(jsonPath("$.result.reviewImageUrl")
				.value("modifyTest"))
			.andExpect(jsonPath("$.result.isPublic")
				.value(false))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e1", role = "CUSTOMER")
	@DisplayName("고객_본인 리뷰 수정 실패_존재하지 않는 리뷰 404")
	@Test
	void modifyReview_404() throws Exception {
		// given
		UUID reviewId = UUID.fromString("1c114e8f-ccd0-42b8-68fa-67fee61d4d13");
		ModifyReviewRequestDto request = ModifyReviewRequestDto.builder()
			.reviewScore(3)
			.reviewContent("modifyTest")
			.reviewImageUrl("modifyTest")
			.isPublic(false)
			.build();

		// when, then
		mockMvc.perform(patch("/api/reviews/{reviewId}", reviewId)
				.header("Authorization", "Bearer {ACCESS_TOKEN}")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(print());
	}
}