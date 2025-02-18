package com.ioteam.order_management_platform.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ioteam.order_management_platform.review.dto.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.ReviewSearchCondition;

public interface ReviewRepositoryCustom {

	Page<AdminReviewResponseDto> searchReviewByCondition(ReviewSearchCondition condition, Pageable pageable);
}
