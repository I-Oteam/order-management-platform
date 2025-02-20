package com.ioteam.order_management_platform.restaurant.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import jakarta.validation.constraints.NotNull;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

	Optional<Restaurant> findByIdAndDeletedAtIsNull(@NotNull UUID resId);

	boolean existsByIdAndDeletedAtIsNull(UUID restaurantId);
}
