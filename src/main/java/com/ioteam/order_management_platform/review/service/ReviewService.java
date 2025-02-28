package com.ioteam.order_management_platform.review.service;

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
import com.ioteam.order_management_platform.review.dto.req.AdminReviewSearchCondition;
import com.ioteam.order_management_platform.review.dto.req.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.req.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.res.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewResponseDto;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.ioteam.order_management_platform.review.repository.ReviewRepository;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;

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

		Review review = reviewRepository.findByReviewIdAndDeletedAtIsNullJoinFetch(reviewId)
			.orElseThrow(() -> new CustomApiException(ReviewException.INVALID_REVIEW_ID));

		// 1. is_public=true 누구나 확인 가능
		// 2. is_public=false 관리자 혹은 작성자 혹은 가게 오너 만 확인 가능
		if (!hasAuthToReadReview(userId, role, review)) {
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		}
		return ReviewResponseDto.from(review);
	}

	@Transactional
	public ReviewResponseDto createReview(UUID userId, CreateReviewRequestDto requestDto) {

		// 리뷰 작성자의 주문 번호가 맞는지 검증 && 레스토랑 아이디도 일치하는지 검증
		Order order = orderRepository.findByOrderIdAndUserIdAndResIdAndDeletedAtIsNullFetchJoin(
				requestDto.getOrderId(), userId, requestDto.getRestaurantId())
			.orElseThrow(() -> new CustomApiException(ReviewException.INVALID_IDS));

		// 완료된 주문인지 검증
		if (!order.isCompletedOrder()) {
			throw new CustomApiException(ReviewException.UNCOMPLETED_ORDER_ID);
		}
		Review review = requestDto.toEntity(order.getUser(), order, order.getRestaurant());
		Review save = reviewRepository.save(review);
		return ReviewResponseDto.from(save);
	}

	@Transactional
	public ReviewResponseDto modifyReview(UUID reviewId, UUID userId, ModifyReviewRequestDto requestDto) {

		// 글 작성자 만 수정 가능
		Review review = reviewRepository.findByReviewIdAndUserIdAndDeletedAtIsNullFetchJoin(reviewId, userId)
			.orElseThrow(() -> new CustomApiException(ReviewException.INVALID_REVIEW_ID));
		review.modify(requestDto);
		return ReviewResponseDto.from(review);
	}

	@Transactional
	public void softDeleteReview(UUID reviewId, UUID userId, UserRoleEnum role) {

		Review review = reviewRepository.findByReviewIdAndDeletedAtIsNull(reviewId)
			.orElseThrow(() -> new CustomApiException(ReviewException.INVALID_REVIEW_ID));

		// 마스터나 매니저가 아니고, 작성자가 아닌 경우 예외 처리
		if (!hasAuthToDeleteReview(userId, role, review)) {
			throw new CustomApiException(BaseException.UNAUTHORIZED_REQ);
		}
		review.softDelete(userId);
	}

	private boolean hasAuthToReadReview(UUID userId, UserRoleEnum role, Review review) {
		return review.getIsPublic()
			|| role.checkIsAdmin()
			|| review.checkIsAuthor(userId)
			|| review.checkIsRestaurantOwner(userId);
	}

	private boolean hasAuthToDeleteReview(UUID userId, UserRoleEnum role, Review review) {
		return role.checkIsAdmin() || review.checkIsAuthor(userId);
	}
}
