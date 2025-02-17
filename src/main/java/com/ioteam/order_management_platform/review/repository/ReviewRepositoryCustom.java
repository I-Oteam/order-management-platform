package com.ioteam.order_management_platform.review.repository;

import com.ioteam.order_management_platform.review.dto.ReviewSearchCondition;
import com.ioteam.order_management_platform.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<Review> searchReviewByCondition(ReviewSearchCondition condition, Pageable pageable);
}
