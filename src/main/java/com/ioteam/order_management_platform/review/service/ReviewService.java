package com.ioteam.order_management_platform.review.service;

import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ReviewResponseDto;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
