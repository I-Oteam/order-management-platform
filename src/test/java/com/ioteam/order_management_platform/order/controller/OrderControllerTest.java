package com.ioteam.order_management_platform.order.controller;

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
import com.ioteam.order_management_platform.utils.security.WithMockCustomUser;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e2", role = "OWNER")
	@DisplayName("주인_가게별 주문 리스트 조회 성공 200")
	@Test
	void searchOrdersByRestaurant() throws Exception {
		// given
		UUID restaurantId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
		LocalDateTime startCreatedAt = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
		LocalDateTime endCreatedAt = LocalDateTime.now().plus(1, ChronoUnit.HOURS);
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("startCreatedAt", startCreatedAt.toString());
		param.add("endCreatedAt", endCreatedAt.toString());

		// when, then
		mockMvc.perform(
				get("/api/orders/restaurants/{restaurantId}", restaurantId)
					.params(param)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.content[0].orderResId")
				.value("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
			.andDo(print());
	}
}