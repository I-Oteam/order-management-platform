package com.ioteam.order_management_platform.restaurant.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

public interface RestaurantRepositoryCustom {

	Page<Restaurant> searchRestaurants(BigDecimal score, Pageable pageable);
}
