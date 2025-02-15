package com.ioteam.order_management_platform.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ModifyReviewRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class ReviewIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("리뷰 생성 성공 201")
    @Test
    void createReview_201() throws Exception {
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
                .andDo(print());
    }

    @DisplayName("리뷰 삭제 성공 200")
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

    @DisplayName("리뷰 수정 성공 200")
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
}