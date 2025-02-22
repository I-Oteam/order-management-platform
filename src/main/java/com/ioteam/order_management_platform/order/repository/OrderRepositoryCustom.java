package com.ioteam.order_management_platform.order.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ioteam.order_management_platform.order.dto.req.OrderByRestaurantSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.OrderByUserSearchCondition;
import com.ioteam.order_management_platform.order.entity.Order;

public interface OrderRepositoryCustom {
	Page<Order> searchOrderByRestaurantAndCondition(UUID resId, OrderByRestaurantSearchCondition condition,
		Pageable pageable);

	Page<Order> searchOrderByUserAndCondition(UUID userId, OrderByUserSearchCondition condition,
		Pageable pageable);

}
