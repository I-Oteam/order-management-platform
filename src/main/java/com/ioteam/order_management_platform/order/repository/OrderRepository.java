package com.ioteam.order_management_platform.order.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	//주문 상태
	List<Order> findByOrderStatusAndCreatedAtBefore(OrderStatus orderStatus, LocalDateTime createdAt);

	@Query("select o from Order o "
		+ "join fetch o.user ou "
		+ "join fetch o.restaurant or "
		+ "where o.orderId = :orderId "
		+ "and o.user.userId = :userId "
		+ "and o.restaurant.resId = :restaurantId "
		+ "and o.deletedAt is null")
	Optional<Order> findByOrderIdAndUserIdAndResIdAndDeletedAtIsNotNullFetchJoin(UUID orderId, UUID userId,
		UUID restaurantId);
}
