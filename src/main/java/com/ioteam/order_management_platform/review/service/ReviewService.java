package com.ioteam.order_management_platform.review.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.global.exception.type.BaseException;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.review.dto.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.ReviewSearchCondition;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.ioteam.order_management_platform.review.repository.ReviewRepository;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final RestaurantRepository restaurantRepository;

	public CommonPageResponse<AdminReviewResponseDto> searchReviewsByCondition(
		ReviewSearchCondition condition, Pageable pageable) {

		Page<AdminReviewResponseDto> reviewDtoList = reviewRepository.searchReviewByCondition(condition, pageable);
		return new CommonPageResponse<>(reviewDtoList);
	}

	public ReviewResponseDto getReview(UUID reviewId, UUID userId, UserRoleEnum role) {

		Review review = reviewRepository.findByReviewIdAndDeletedAtIsNull(reviewId)
			.orElseThrow(() -> {
				throw new CustomApiException(ReviewException.INVALID_REVIEW_ID);
			});

		// 1. is_public=true 누구나 확인 가능
		// 2. is_public=false 작성자와 가게 오너, 관리자만 확인 가능
		if (review.getIsPublic()
			|| review.getUser().getUserId().equals(userId)
			//|| review.getRestaurant().getResOwnerId().equals(userId)
			|| List.of(UserRoleEnum.MANAGER, UserRoleEnum.MASTER).contains(role)) {
			return ReviewResponseDto.from(review);
		}
		throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
	}

	@Transactional
	public ReviewResponseDto createReview(UUID userId, CreateReviewRequestDto requestDto) {

		User referenceUser = userRepository.getReferenceById(userId);
		Order referenceOrder = orderRepository.getReferenceById(requestDto.getOrderId());
		Restaurant referenceRestaurant = restaurantRepository.getReferenceById(requestDto.getRestaurantId());

		Review review = requestDto.toEntity(referenceUser, referenceOrder, referenceRestaurant);
		Review save = reviewRepository.save(review);
		return ReviewResponseDto.from(save);
	}

	@Transactional
	public ReviewResponseDto modifyReview(UUID reviewId, UUID userId, ModifyReviewRequestDto requestDto) {

		// 글 작성자만 수정 가능
		Review review = reviewRepository.findByReviewIdAndUser_userIdAndDeletedAtIsNull(reviewId, userId)
			.orElseThrow(() -> {
				throw new CustomApiException(ReviewException.INVALID_REVIEW_ID);
			});
		review.modify(requestDto);
		return ReviewResponseDto.from(review);
	}

	@Transactional
	public void softDeleteReview(UUID reviewId, UUID userId, UserRoleEnum role) {

		Review review = reviewRepository.findByReviewIdAndDeletedAtIsNull(reviewId)
			.orElseThrow(() -> {
				throw new CustomApiException(ReviewException.INVALID_REVIEW_ID);
			});

		// 매니저가 아니고, 작성자가 아닌 경우 예외 처리
		if (!role.equals(UserRoleEnum.MANAGER) && !review.getUser().getUserId().equals(userId)) {
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		}
		review.softDelete();
	}
}
