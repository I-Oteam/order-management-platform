package com.ioteam.order_management_platform.restaurant.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

}
