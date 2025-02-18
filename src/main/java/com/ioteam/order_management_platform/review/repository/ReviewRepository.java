package com.ioteam.order_management_platform.review.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewRepositoryCustom {
	Optional<Review> findByReviewIdAndDeletedAtIsNull(UUID reviewId);

	//@Query("select r from Review r where r.reviewId = :reviewId and r.user.userId = :reviewUserId and r.deletedAt is null")
	Optional<Review> findByReviewIdAndUser_userIdAndDeletedAtIsNull(UUID reviewId, UUID reviewUserId);
}
