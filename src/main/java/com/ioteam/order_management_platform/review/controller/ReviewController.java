package com.ioteam.order_management_platform.review.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.review.dto.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.ReviewSearchCondition;
import com.ioteam.order_management_platform.review.service.ReviewService;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "리뷰", description = "리뷰 API")
public class ReviewController {

	private final ReviewService reviewService;

	@GetMapping("/reviews/all")
	public ResponseEntity<CommonResponse<CommonPageResponse<AdminReviewResponseDto>>> searchReviews(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		ReviewSearchCondition condition,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		CommonPageResponse<AdminReviewResponseDto> pageResponse = reviewService.searchReviewsByCondition(condition,
			pageable);
		return ResponseEntity.ok(new CommonResponse<>(
			SuccessCode.REVIEW_SEARCH.getMessage(),
			pageResponse));
	}

	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<CommonResponse<ReviewResponseDto>> getReview(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID reviewId) {

		ReviewResponseDto responseDto = reviewService.getReview(
			reviewId); //, userDetails.getUser().getUserId(), userDetails.getUser().getRole());
		return ResponseEntity.ok(new CommonResponse<>(
			SuccessCode.REVIEW_CREATE.getMessage(),
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
				SuccessCode.REVIEW_CREATE.getMessage(),
				responseDto));
	}

	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<CommonResponse<Void>> softDeleteReview(@PathVariable UUID reviewId) {

		reviewService.softDeleteReview(reviewId);
		return ResponseEntity.ok(new CommonResponse<>(
			SuccessCode.REVIEW_DELETE.getMessage(),
			null));
	}

	@PatchMapping("/reviews/{reviewId}")
	public ResponseEntity<CommonResponse<ReviewResponseDto>> modifyReview(
		@PathVariable UUID reviewId,
		@RequestBody @Validated ModifyReviewRequestDto requestDto) {

		ReviewResponseDto responseDto = reviewService.modifyReview(reviewId, requestDto);
		return ResponseEntity.ok(new CommonResponse<>(
			SuccessCode.REVIEW_MODIFY.getMessage(),
			responseDto));
	}
}
