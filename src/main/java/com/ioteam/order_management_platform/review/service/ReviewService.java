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
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.review.dto.req.AdminReviewSearchCondition;
import com.ioteam.order_management_platform.review.dto.req.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.req.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.res.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewResponseDto;
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

	public CommonPageResponse<AdminReviewResponseDto> searchReviewAdminByCondition(
		AdminReviewSearchCondition condition, Pageable pageable) {

		Page<AdminReviewResponseDto> reviewDtoPage = reviewRepository.searchReviewAdminByCondition(condition, pageable);
		return new CommonPageResponse<>(reviewDtoPage);
	}

	public CommonPageResponse<ReviewResponseDto> searchReviewByUser(UUID userId, UUID requiredUserId,
		Pageable pageable) {

		if (!userId.equals(requiredUserId))
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		Page<ReviewResponseDto> reviewDtoPage = reviewRepository.searchReviewByUser(userId, pageable);
		return new CommonPageResponse<>(reviewDtoPage);
	}

	public CommonPageResponse<ReviewResponseDto> searchReviewByRestaurant(UUID userId, UUID resId, Pageable pageable) {

		Page<ReviewResponseDto> reviewDtoPage = reviewRepository.searchReviewByRestaurant(userId, resId, pageable);
		return new CommonPageResponse<>(reviewDtoPage);
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
			// todo. owner 설정되면 주석 풀고 수정
			//|| review.getRestaurant().getResOwnerId().equals(userId)
			|| List.of(UserRoleEnum.MANAGER, UserRoleEnum.MASTER).contains(role)) {
			return ReviewResponseDto.from(review);
		}
		throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
	}

	@Transactional
	public ReviewResponseDto createReview(UUID userId, CreateReviewRequestDto requestDto) {

		User referenceUser = userRepository.getReferenceById(userId);
		Order order = orderRepository.findById(requestDto.getOrderId())
			.orElseThrow(() -> {
				throw new CustomApiException(ReviewException.INVALID_ORDER_ID);
			});

		// 리뷰 작성자의 주문 번호가 맞는지 검증 && 완료된 주문인지 검증
		if (!order.getOrderUserId().toString().equals(userId.toString())
			&& order.getOrderStatus().equals(OrderStatus.COMPLETED)) {
			throw new CustomApiException(ReviewException.UNAUTH_ORDER_ID);
		}

		Restaurant referenceRestaurant = restaurantRepository.getReferenceById(requestDto.getRestaurantId());

		Review review = requestDto.toEntity(referenceUser, order, referenceRestaurant);
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
