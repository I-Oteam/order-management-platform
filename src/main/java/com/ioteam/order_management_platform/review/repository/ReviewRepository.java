package com.ioteam.order_management_platform.review.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ioteam.order_management_platform.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewRepositoryCustom {
	Optional<Review> findByReviewIdAndDeletedAtIsNull(UUID reviewId);

	@Query("select r from Review r "
		+ "join fetch r.user ru "
		+ "join fetch r.restaurant rt "
		+ "where r.reviewId = :reviewId "
		+ "and r.deletedAt is null")
	Optional<Review> findByReviewIdAndDeletedAtIsNullJoinFetch(UUID reviewId);

	@Query("select r from Review r "
		+ "join fetch r.user ru "
		+ "join fetch r.restaurant rt "
		+ "where r.reviewId = :reviewId "
		+ "and r.user.userId = :userId "
		+ "and r.deletedAt is null")
	Optional<Review> findByReviewIdAndUserIdAndDeletedAtIsNullFetchJoin(UUID reviewId, UUID userId);
}
