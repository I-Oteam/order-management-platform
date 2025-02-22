package com.ioteam.order_management_platform.restaurant.repository;

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

}

