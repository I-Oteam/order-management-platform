package com.ioteam.order_management_platform.menu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteam.order_management_platform.menu.dto.req.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.entity.MenuStatus;
import com.ioteam.order_management_platform.utils.security.WithMockCustomUser;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class MenuIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e3", role = "MANAGER")
	@DisplayName("매니저_상품 생성 성공 201")
	@Test
	void createMenu_201() throws Exception {
		// given
		CreateMenuRequestDto requestDto = CreateMenuRequestDto.builder()
			.resId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
			.rmName("test")
			.rmPrice(BigDecimal.valueOf(10000))
			.rmImageUrl("test")
			.rmDescription("test")
			.rmStatus(MenuStatus.valueOf("ON_SALE"))
			.isPublic(true)
			.build();

		// when, then
		mockMvc.perform(post("/api/menus")
				.header("Authorization", "Bearer {ACCESS_TOKEN}")
				.content(objectMapper.writeValueAsString(requestDto))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.rmName")
				.value("test"))
			.andExpect(jsonPath("$.result.rmPrice").value(BigDecimal.valueOf(10000)))
			.andExpect(jsonPath("$.result.rmImageUrl").value("test"))
			.andExpect(jsonPath("$.result.rmDescription").value("test"))
			.andExpect(jsonPath("$.result.rmStatus").value(MenuStatus.ON_SALE.name()))
			.andExpect(jsonPath("$.result.isPublic").value(true))
			.andDo(print());
	}
}