package com.ioteam.order_management_platform.menu.controller;

import static org.junit.jupiter.api.Assertions.*;
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
import com.ioteam.order_management_platform.menu.dto.req.UpdateMenuRequestDto;
import com.ioteam.order_management_platform.menu.entity.MenuStatus;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;
import com.ioteam.order_management_platform.utils.security.WithMockCustomUser;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class MenuIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MenuRepository menuRepository;

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e3", role = "MANAGER")
	@DisplayName("매니저_상품 생성 성공 201")
	@Test
	void createMenu_Manager_201() throws Exception {
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
		mockMvc.perform(
				post("/api/menus")
					.header("Authorization", "Bearer {ACCESS_TOKEN}")
					.content(objectMapper.writeValueAsString(requestDto))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.rmName")
				.value("test"))
			.andExpect(jsonPath("$.result.rmPrice")
				.value(BigDecimal.valueOf(10000)))
			.andExpect(jsonPath("$.result.rmImageUrl")
				.value("test"))
			.andExpect(jsonPath("$.result.rmDescription")
				.value("test"))
			.andExpect(jsonPath("$.result.rmStatus")
				.value(MenuStatus.ON_SALE.name()))
			.andExpect(jsonPath("$.result.isPublic")
				.value(true))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e3", role = "MANAGER")
	@DisplayName("매니저_특정 가게 상품 전체 조회 성공 200")
	@Test
	void getAllMenus_Manager_200() throws Exception {
		// given
		UUID restaurantId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

		// when, then
		mockMvc.perform(
				get("/api/menus/restaurant/{restaurant_id}", restaurantId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.totalElements")
				.value(3))
			.andExpect(jsonPath("$.result.content[0].rmName")
				.value("열파르타"))
			.andExpect(jsonPath("$.result.content[2].isPublic")
				.value(false))
			.andDo(print());
	}

	@WithMockCustomUser
	@DisplayName("고객_특정 가게 상품 전체 조회 성공 200")
	@Test
	void getAllMenus_Customer_200() throws Exception {
		// given
		UUID restaurantId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

		// when, then
		mockMvc.perform(
				get("/api/menus/restaurant/{restaurant_id}", restaurantId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.totalElements")
				.value(2))
			.andExpect(jsonPath("$.result.content[0].rmName")
				.value("열파르타"))
			.andExpect(jsonPath("$.result.content[1].rmStatus")
				.value("SOLD_OUT"))
			.andDo(print());
	}

	@WithMockCustomUser
	@DisplayName("고객_상품 상세 조회 성공 200")
	@Test
	void getMenuDetail_Manager_200() throws Exception {
		// given
		UUID menuId = UUID.fromString("439f222b-0cbb-4600-a989-e7fdabf120d6");

		// when, then
		mockMvc.perform(
				get("/api/menus/{menu_id}", menuId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.rmName")
				.value("열리오올리오"))
			.andExpect(jsonPath("$.result.rmStatus")
				.value("SOLD_OUT"))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e3", role = "MANAGER")
	@DisplayName("매니저_상품 수정 성공 200")
	@Test
	void modifyMenu_Manager_200() throws Exception {
		// given
		UUID menuId = UUID.fromString("439f222b-0cbb-4600-a989-e7fdabf120d6");
		UpdateMenuRequestDto requestDto = UpdateMenuRequestDto.builder()
			.rmName("test")
			.rmPrice(BigDecimal.valueOf(0))
			.rmStatus(MenuStatus.ON_SALE)
			.build();

		// when, then
		mockMvc.perform(
				patch("/api/menus/{menu_id}", menuId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}")
					.content(objectMapper.writeValueAsString(requestDto))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.rmName")
				.value("test"))
			.andExpect(jsonPath("$.result.rmPrice")
				.value(BigDecimal.valueOf(0)))
			.andExpect(jsonPath("$.result.rmStatus")
				.value("ON_SALE"))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e2", role = "OWNER")
	@DisplayName("가게주인_상품 수정 성공 200")
	@Test
	void modifyMenu_Owner_200() throws Exception {
		// given
		UUID menuId = UUID.fromString("439f222b-0cbb-4600-a989-e7fdabf120d6");
		UpdateMenuRequestDto requestDto = UpdateMenuRequestDto.builder()
			.rmName("Owner_test")
			.rmPrice(BigDecimal.valueOf(10))
			.rmStatus(MenuStatus.HIDDEN)
			.build();

		// when, then
		mockMvc.perform(
				patch("/api/menus/{menu_id}", menuId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}")
					.content(objectMapper.writeValueAsString(requestDto))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.rmName")
				.value("Owner_test"))
			.andExpect(jsonPath("$.result.rmPrice")
				.value(BigDecimal.valueOf(10)))
			.andExpect(jsonPath("$.result.rmStatus")
				.value("HIDDEN"))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e2", role = "OWNER")
	@DisplayName("가게주인_상품 숨김 처리 성공 200")
	@Test
	void hiddenMenu_Owner_200() throws Exception {
		// given
		UUID menuId = UUID.fromString("439f222b-0cbb-4600-a989-e7fdabf120d6");

		// when, then
		mockMvc.perform(
				patch("/api/menus/{menu_id}/hidden", menuId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result.rmStatus")
				.value("HIDDEN"))
			.andDo(print());
	}

	@WithMockCustomUser(userId = "d2ed72d8-090a-4efb-abe4-7acbdce120e2", role = "OWNER")
	@DisplayName("가게주인_상품 삭제 처리 성공 200")
	@Test
	void deleteMenu_Owner_200() throws Exception {
		// given
		UUID menuId = UUID.fromString("439f222b-0cbb-4600-a989-e7fdabf120d6");

		// when
		mockMvc.perform(
				delete("/api/menus/{menu_id}", menuId)
					.header("Authorization", "Bearer {ACCESS_TOKEN}"))
			.andExpect(status().isNoContent())
			.andDo(print());

		// then
		boolean exists = menuRepository.findByRmIdAndDeletedAtIsNull(menuId).isPresent();
		assertFalse(exists, "soft delete 실패!");
	}
}