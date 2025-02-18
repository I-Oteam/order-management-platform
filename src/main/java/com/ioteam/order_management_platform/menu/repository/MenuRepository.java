package com.ioteam.order_management_platform.menu.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
	List<Menu> findByRestaurant_ResId(UUID restaurantId);
}
