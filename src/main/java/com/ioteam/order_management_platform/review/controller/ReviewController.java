package com.ioteam.order_management_platform.review.controller;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.review.dto.*;
import com.ioteam.order_management_platform.review.service.ReviewService;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/all")
    public ResponseEntity<CommonResponse<CommonPageResponse<AdminReviewResponseDto>>> searchReviews(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            ReviewSearchCondition condition,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        CommonPageResponse<AdminReviewResponseDto> pageResponse = reviewService.searchReviewsByCondition(condition, pageable);
        return ResponseEntity.ok(new CommonResponse <>(
                "리뷰가 성공적으로 조회되었습니다.",
                pageResponse));
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<CommonResponse<ReviewResponseDto>> getReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID reviewId) {

        ReviewResponseDto responseDto = reviewService.getReview(
                reviewId, userDetails.getUser().getUserId(), userDetails.getUser().getRole());
        return ResponseEntity.ok(new CommonResponse<>(
                "리뷰가 성공적으로 조회되었습니다.",
                responseDto));
    }

    @PostMapping("/reviews")
    public ResponseEntity<CommonResponse<ReviewResponseDto>> createReview(
            @RequestBody @Validated CreateReviewRequestDto requestDto) {

        ReviewResponseDto responseDto = reviewService.createReview(requestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/reviews/" + responseDto.getReviewId().toString())
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .body(new CommonResponse<>(
                "리뷰가 성공적으로 생성되었습니다.",
                responseDto));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<CommonResponse<Void>> softDeleteReview(@PathVariable UUID reviewId) {

        reviewService.softDeleteReview(reviewId);
        return ResponseEntity.ok(new CommonResponse<>(
                "리뷰가 성공적으로 삭제되었습니다.",
                null));
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<CommonResponse<ReviewResponseDto>> modifyReview(
            @PathVariable UUID reviewId,
            @RequestBody @Validated ModifyReviewRequestDto requestDto) {

        ReviewResponseDto responseDto = reviewService.modifyReview(reviewId, requestDto);
        return ResponseEntity.ok(new CommonResponse<>(
                "리뷰가 성공적으로 수정되었습니다.",
                responseDto));
    }
}
