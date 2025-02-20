package com.ioteam.order_management_platform.review.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ioteam.order_management_platform.review.dto.req.AdminReviewSearchCondition;
import com.ioteam.order_management_platform.review.dto.req.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.req.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.res.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewResponseDto;
import com.ioteam.order_management_platform.review.service.ReviewService;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "리뷰", description = "리뷰 API")
public class ReviewController {

	private final ReviewService reviewService;

	@Operation(summary = "전체 리뷰 조회", description = "전체 리뷰 조회는 'MANAGER', 'MASTER' 만 가능")
	@PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
	@GetMapping("/reviews/admin")
	public ResponseEntity<CommonResponse<CommonPageResponse<AdminReviewResponseDto>>> searchReviewAdmin(
		AdminReviewSearchCondition condition,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {

		CommonPageResponse<AdminReviewResponseDto> pageResponse = reviewService.searchReviewAdminByCondition(condition,
			pageable);
		return ResponseEntity.ok(new CommonResponse<>(
			SuccessCode.REVIEW_SEARCH,
			pageResponse));
	}

	@Operation(summary = "유저별 리뷰 조회", description = "유저별 리뷰 조회는 'CUSTOMER' 만 가능")
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/reviews/users/{userId}")
	public ResponseEntity<CommonResponse<CommonPageResponse<ReviewResponseDto>>> searchReviewsByUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID userId,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {

		CommonPageResponse<ReviewResponseDto> pageResponse = reviewService.searchReviewByUser(
			userDetails.getUserId(), userId, pageable);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.REVIEW_SEARCH, pageResponse));
	}

	@Operation(summary = "가게별 리뷰 조회", description = "가게별 리뷰 조회는 인증된 사용자들 모두 가능")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reviews/restaurants/{resId}")
	public ResponseEntity<CommonResponse<CommonPageResponse<ReviewResponseDto>>> searchReviewsByRestaurant(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID resId,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {

		CommonPageResponse<ReviewResponseDto> pageResponse = reviewService.searchReviewByRestaurant(
			userDetails.getUserId(), resId, pageable);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.REVIEW_SEARCH, pageResponse));
	}

	@Operation(summary = "리뷰 조회", description = "리뷰 조회는 인증된 사용자만 가능")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<CommonResponse<ReviewResponseDto>> getReview(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID reviewId) {

		ReviewResponseDto responseDto = reviewService.getReview(reviewId, userDetails.getUserId(),
			userDetails.getRole());
		return ResponseEntity.ok(new CommonResponse<>(
			SuccessCode.REVIEW_SEARCH,
			responseDto));
	}

	@Operation(summary = "리뷰 생성", description = "리뷰 생성은 'CUSTOMER' 만 가능")
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/reviews")
	public ResponseEntity<CommonResponse<ReviewResponseDto>> createReview(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Validated CreateReviewRequestDto requestDto) {

		ReviewResponseDto responseDto = reviewService.createReview(userDetails.getUserId(), requestDto);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/reviews/" + responseDto.getReviewId().toString())
			.build()
			.toUri();
		return ResponseEntity.created(location)
			.body(new CommonResponse<>(SuccessCode.REVIEW_CREATE, responseDto));
	}

	@Operation(summary = "리뷰 수정", description = "리뷰 수정은 'CUSTOMER' 만 가능")
	@PreAuthorize("hasRole('CUSTOMER')")
	@PatchMapping("/reviews/{reviewId}")
	public ResponseEntity<CommonResponse<ReviewResponseDto>> modifyReview(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID reviewId,
		@RequestBody @Validated ModifyReviewRequestDto requestDto) {

		ReviewResponseDto responseDto = reviewService.modifyReview(reviewId, userDetails.getUserId(), requestDto);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.REVIEW_MODIFY, responseDto));
	}

	@Operation(summary = "리뷰 삭제", description = "리뷰 삭제는 'MANAGER', 'MANAGER', 'CUSTOMER' 만 가능")
	@PreAuthorize("hasAnyRole('MASTER', 'MANAGER', 'CUSTOMER')")
	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<CommonResponse<Void>> softDeleteReview(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID reviewId) {

		reviewService.softDeleteReview(reviewId, userDetails.getUserId(), userDetails.getRole());
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.REVIEW_DELETE, null));
	}
}
