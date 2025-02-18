package com.ioteam.order_management_platform.review.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.global.exception.type.BaseException;
import com.ioteam.order_management_platform.review.dto.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.ReviewSearchCondition;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.ioteam.order_management_platform.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;

	public CommonPageResponse<AdminReviewResponseDto> searchReviewsByCondition(
		ReviewSearchCondition condition, Pageable pageable) {

		Page<AdminReviewResponseDto> reviewDtoList = reviewRepository.searchReviewByCondition(condition, pageable)
			.map(AdminReviewResponseDto::from);
		return new CommonPageResponse<>(reviewDtoList);
	}

	public ReviewResponseDto getReview(UUID reviewId) { //, UUID userId, UserRoleEnum role) {

		Review review = reviewRepository.findByReviewIdAndDeletedAtIsNull(reviewId)
			.orElseThrow(() -> {
				throw new CustomApiException(ReviewException.INVALID_REVIEW_ID);
			});

		// 1. is_public=true 누구나 확인 가능
		// 2. is_public=false 작성자와 가게 오너, 관리자만 확인 가능
		if (review.getIsPublic()) {
			// || review.getUser().getUserId().equals(userId)
			// || List.of("OWNER", "MANAGER", "MASTER").contains(role.name())) {
			return ReviewResponseDto.from(review);
		}
		throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
	}

	@Transactional
	public ReviewResponseDto createReview(CreateReviewRequestDto requestDto) {

		Review reviewEntity = requestDto.toEntity();
		Review save = reviewRepository.save(reviewEntity);
		return ReviewResponseDto.from(save);
	}

	@Transactional
	public void softDeleteReview(UUID reviewId) {
		Review review = reviewRepository.findByReviewIdAndDeletedAtIsNull(reviewId)
			.orElseThrow(() -> {
				throw new CustomApiException(ReviewException.INVALID_REVIEW_ID);
			});
		review.softDelete();
	}

	@Transactional
	public ReviewResponseDto modifyReview(UUID reviewId, ModifyReviewRequestDto requestDto) {

		Review review = reviewRepository.findByReviewIdAndDeletedAtIsNull(reviewId)
			.orElseThrow(() -> {
				throw new CustomApiException(ReviewException.INVALID_REVIEW_ID);
			});
		review.modify(requestDto);
		return ReviewResponseDto.from(review);
	}
}
