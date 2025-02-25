package com.ioteam.order_management_platform.order.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, UUID>, OrderRepositoryCustom {

	@Query("select o from Order o "
		+ "join fetch o.user ou "
		+ "join fetch o.restaurant or "
		+ "where o.orderId = :orderId "
		+ "and o.user.userId = :userId "
		+ "and o.restaurant.resId = :restaurantId "
		+ "and o.deletedAt is null")
	Optional<Order> findByOrderIdAndUserIdAndResIdAndDeletedAtIsNullFetchJoin(UUID orderId, UUID userId,
		UUID restaurantId);

	//주문 취소
	@Modifying
	@Query("UPDATE Order o " +
		"SET o.orderStatus = :cancelStatus " +
		"WHERE o.orderId = :orderId " +
		"AND ( " +
		"    (:role = 'MANAGER') " +
		" OR (:role = 'OWNER' AND o.restaurant.owner.userId = :userId) " +
		" OR (:role = 'CUSTOMER' AND o.user.userId = :userId AND o.createdAt > :minTime) " +
		")")
	int cancelOrder(@Param("orderId") UUID orderId,
		@Param("role") String role,
		@Param("userId") UUID userId,
		@Param("minTime") LocalDateTime minTime,
		@Param("cancelStatus") OrderStatus cancelStatus);

	Optional<Order> findByOrderIdAndDeletedAtIsNull(UUID orderId);

	@Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.orderId = :orderId AND o.user.userId = :userId AND o.deletedAt IS NULL")
	Optional<Order> findValidOrderForPayment(@Param("orderId") UUID orderId, @Param("userId") UUID userId);

	@Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.orderId = :orderId AND o.deletedAt IS NULL")
	Optional<Order> findByOrderIdAndDeletedAtIsNullFetchJoinUser(UUID orderId);
}