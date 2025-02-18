package com.ioteam.order_management_platform.review.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ioteam.order_management_platform.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewRepositoryCustom {
	Optional<Review> findByReviewIdAndDeletedAtIsNull(UUID reviewId);
}
