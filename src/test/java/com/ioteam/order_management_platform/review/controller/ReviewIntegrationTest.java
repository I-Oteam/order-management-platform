package com.ioteam.order_management_platform.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ReviewIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("리뷰_생성_성공_201")
    @Test
    void createReview() throws Exception {
        // given
        CreateReviewRequestDto request = CreateReviewRequestDto.builder()
                .reviewOrderId(UUID.fromString("d8ef5ca7-2b3c-49bb-9c6d-425c85036dec"))
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
//                .andExpect(jsonPath("$.result.reviewOrderId")
//                        .value("d8ef5ca7-2b3c-49bb-9c6d-425c85036dec"))
                .andDo(print());
    }
}