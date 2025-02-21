package com.ioteam.order_management_platform.restaurant.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import jakarta.validation.constraints.NotNull;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

	Optional<Restaurant> findByResIdAndDeletedAtIsNull(@NotNull UUID resId);

	boolean existsByResIdAndDeletedAtIsNull(UUID restaurantId);


	Page<Restaurant> findAllByDeletedAtIsNull(Pageable pageable);
  
	boolean existsByResIdAndOwner_userIdAndDeletedAtIsNull(UUID resId, UUID userId);
}

