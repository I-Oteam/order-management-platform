package com.ioteam.order_management_platform.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteam.order_management_platform.category.dto.CategoryRequestDto;
import com.ioteam.order_management_platform.category.dto.CategoryResponseDto;
import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Controller Test : 카테고리 생성 테스트")
    void createCategory() throws Exception {
        // given - mock 객체 설정
        CategoryRequestDto requestDto = new CategoryRequestDto("일식");
        Category category = new Category(requestDto);
        CategoryResponseDto responseDto = new CategoryResponseDto(category);

        // mock 서비스 메서드
        when(categoryService.createCategory(requestDto)).thenReturn(responseDto);

        // when & then - POST 요청 보내고 응답 검증
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(result -> System.out.println("응답 : " + result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.result.rcName").value("일식"));


    }
}