package com.ioteam.order_management_platform.review.controller;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ReviewResponseDto;
import com.ioteam.order_management_platform.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<CommonResponse<ReviewResponseDto>> createReview(@RequestBody CreateReviewRequestDto requestDto) {

        ReviewResponseDto responseDto = reviewService.createReview(requestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/reviews/" + responseDto.getReviewId().toString())
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .body(new CommonResponse<ReviewResponseDto>(
                "리뷰가 성공적으로 생성되었습니다.",
                responseDto));
    }
}
