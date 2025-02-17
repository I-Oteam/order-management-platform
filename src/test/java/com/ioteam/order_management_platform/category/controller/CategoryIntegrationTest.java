package com.ioteam.order_management_platform.category.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteam.order_management_platform.category.dto.CategoryResponseDto;
import com.ioteam.order_management_platform.category.dto.CreateCategoryRequestDto;
import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.category.service.CategoryService;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CategoryIntegrationTest {

	@Mock
	private CategoryService categoryService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	private UserDetailsImpl userDetails;

	@BeforeEach
	void setUp() {
		User user = new User("테스트 닉네임", "테스트", "테스트비밀번호", "테스트이메일@테스트.테스트", UserRoleEnum.MANAGER);
		userDetails = new UserDetailsImpl(user);

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
		SecurityContextHolder.setContext(context);
	}

	@Test
	@DisplayName("Controller Test : 카테고리 생성 테스트")
	void createCategory_201() throws Exception {
		// given - mock 객체 설정
		CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto("일식");
		Category category = new Category(requestDto);
		CategoryResponseDto responseDto = new CategoryResponseDto(category);

		// mock 서비스 메서드
		when(categoryService.createCategory(requestDto, userDetails)).thenReturn(responseDto);

		// when & then - POST 요청 보내고 응답 검증
		mockMvc.perform(post("/api/categories")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(jsonPath("$.result.rcName").value("일식"))
			.andDo(print());

	}
	
}