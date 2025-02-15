package com.ioteam.order_management_platform.review.service;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ReviewResponseDto;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.ioteam.order_management_platform.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

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
