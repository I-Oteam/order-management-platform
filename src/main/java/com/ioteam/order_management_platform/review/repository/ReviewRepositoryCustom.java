package com.ioteam.order_management_platform.review.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ioteam.order_management_platform.review.dto.req.AdminReviewSearchCondition;
import com.ioteam.order_management_platform.review.dto.res.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewResponseDto;

public interface ReviewRepositoryCustom {

	Page<AdminReviewResponseDto> searchReviewAdminByCondition(AdminReviewSearchCondition condition, Pageable pageable);

	Page<ReviewResponseDto> searchReviewByUser(UUID userId, Pageable pageable);
}
