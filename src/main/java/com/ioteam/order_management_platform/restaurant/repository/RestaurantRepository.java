package com.ioteam.order_management_platform.restaurant.repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import jakarta.validation.constraints.NotNull;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

	Optional<Restaurant> findByResIdAndDeletedAtIsNull(@NotNull UUID resId);

	boolean existsByResIdAndDeletedAtIsNull(UUID restaurantId);

	// Page<Restaurant> findAllByDeletedAtIsNull(Pageable pageable);

	boolean existsByResIdAndOwner_userIdAndDeletedAtIsNull(UUID resId, UUID userId);

	@Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.restaurantScore WHERE r.resId = :resId AND r.deletedAt IS NULL")
	Optional<Restaurant> findByResIdWithScoreAndDeletedAtIsNull(@Param("resId") UUID resId);

	@Query("""
		    SELECT r FROM Restaurant r
		    LEFT JOIN FETCH r.restaurantScore
		    WHERE r.deletedAt IS NULL
		""")
	Page<Restaurant> findAllWithScoreByDeletedAtIsNull(Pageable pageable);

	@Query("SELECT r FROM Restaurant r JOIN FETCH r.restaurantScore rs WHERE rs.rsScore >= :minScore AND rs.rsScore < :maxScore AND r.deletedAt IS NULL")
	Page<Restaurant> findRestaurantsByScoreRangeAndDeletedAtIsNull(@Param("minScore") BigDecimal minScore,
		@Param("maxScore") BigDecimal maxScore, Pageable pageable);

	@Query("SELECT r FROM Restaurant r JOIN FETCH r.restaurantScore rs WHERE r.deletedAt IS NULL ORDER BY rs.rsScore DESC")
	Page<Restaurant> findAllWithScoreSortedByScoreDescAndDeletedAtIsNull(Pageable pageable);

	Page<Restaurant> findAllByCategory_RcNameAndDeletedAtIsNull(String rcName, Pageable pageable);
}

