package com.ioteam.order_management_platform.review.service;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.review.dto.*;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.ioteam.order_management_platform.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    @Transactional
    public ReviewResponseDto createReview(CreateReviewRequestDto requestDto) {

        Review reviewEntity = new Review(requestDto);
        Review save = reviewRepository.save(reviewEntity);
        return save.toResponseDto();
    }

    @Transactional
    public void softDeleteReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    throw new CustomApiException(ReviewException.INVALID_REVIEW_ID);
                });
        review.softDelete();
    }

    @Transactional
    public ReviewResponseDto modifyReview(UUID reviewId, ModifyReviewRequestDto requestDto) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    throw new CustomApiException(ReviewException.INVALID_REVIEW_ID);
                });
        review.modify(requestDto);
        return review.toResponseDto();
    }
}
