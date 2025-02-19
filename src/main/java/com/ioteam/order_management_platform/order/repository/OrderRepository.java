package com.ioteam.order_management_platform.order.repository;

import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	//주문 상태
	List<Order> findByOrderStatusAndCreatedAtBefore(OrderStatus orderStatus, LocalDateTime createdAt);
}
