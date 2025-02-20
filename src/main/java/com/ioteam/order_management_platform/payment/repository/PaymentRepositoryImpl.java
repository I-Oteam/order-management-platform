package com.ioteam.order_management_platform.payment.repository;

import static com.ioteam.order_management_platform.order.entity.QOrder.*;
import static com.ioteam.order_management_platform.payment.entity.QPayment.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.payment.dto.req.AdminPaymentSearchCondition;
import com.ioteam.order_management_platform.payment.dto.res.AdminPaymentResponseDto;
import com.ioteam.order_management_platform.payment.dto.res.PaymentResponseDto;
import com.ioteam.order_management_platform.payment.exception.PaymentException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<AdminPaymentResponseDto> searchPaymentAdminByCondition(AdminPaymentSearchCondition condition,
		Pageable pageable) {
		List<Tuple> results = queryFactory
			.select(
				payment.paymentId,
				order.user.userId, // customerId 매핑
				order.restaurant.resId, // restaurantId 매핑
				payment.paymentTotal,
				payment.paymentMethod,
				payment.paymentNumber,
				payment.paymentStatus,
				payment.createdAt
			)
			.from(payment)
			.leftJoin(order).on(order.orderId.eq(payment.order.orderId))
			.where(
				eqOrderId(condition.getOrderId()),
				eqCustomerId(condition.getCustomerId()),
				eqRestaurantId(condition.getRestaurantId()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenPaymentTotal(condition.getMinPaymentTotal(), condition.getMaxPaymentTotal()),
				eqPaymentMethod(condition.getPaymentMethod()),
				eqPaymentStatus(condition.getPaymentStatus()),
				isDeleted(condition.getIsDeleted())
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// Fetch the total count
		Long totalCount = queryFactory
			.select(payment.count())
			.from(payment)
			.leftJoin(order).on(order.orderId.eq(payment.order.orderId))
			.where(
				eqOrderId(condition.getOrderId()),
				eqCustomerId(condition.getCustomerId()),
				eqRestaurantId(condition.getRestaurantId()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenPaymentTotal(condition.getMinPaymentTotal(), condition.getMaxPaymentTotal()),
				eqPaymentMethod(condition.getPaymentMethod()),
				eqPaymentStatus(condition.getPaymentStatus()),
				isDeleted(condition.getIsDeleted())
			)
			.fetchOne();
		// 총 개수 가져오기
		long total = (totalCount != null) ? totalCount : 0L;

		List<AdminPaymentResponseDto> dtoList = results.stream()
			.map(tuple -> AdminPaymentResponseDto.builder()
				.paymentId(tuple.get(payment.paymentId))
				.customerId(tuple.get(order.user.userId)) // 주문한 고객 ID
				.restaurantId(tuple.get(order.restaurant.resId)) // 가게 ID
				.paymentTotal(tuple.get(payment.paymentTotal))
				.paymentMethod(tuple.get(payment.paymentMethod))
				.paymentNumber(tuple.get(payment.paymentNumber))
				.paymentStatus(tuple.get(payment.paymentStatus))
				.createdAt(tuple.get(payment.createdAt))
				.build())
			.toList();

		return new PageImpl<>(dtoList, pageable, total);
	}

	@Override
	public Page<PaymentResponseDto> searchPaymentByUser(UUID userId, Pageable pageable) {

		return null;
	}

	@Override
	public Page<PaymentResponseDto> searchPaymentByRestaurant(UUID userId, UUID restaurantId, Pageable pageable) {
		return null;
	}

	private BooleanExpression eqOrderId(UUID orderId) {
		if (orderId == null)
			return null;
		return payment.order.orderId.eq(orderId);
	}

	private BooleanExpression eqCustomerId(UUID customerId) {
		return customerId == null ? null : order.user.userId.eq(customerId);
	}

	private BooleanExpression eqRestaurantId(UUID restaurantId) {
		return restaurantId == null ? null : order.restaurant.resId.eq(restaurantId);
	}

	private BooleanExpression betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {
		if (startCreatedAt == null || endCreatedAt == null)
			return null;
		if (startCreatedAt.isAfter(endCreatedAt))
			throw new CustomApiException(PaymentException.INVALID_PERIOD);
		return payment.createdAt.between(startCreatedAt, endCreatedAt);
	}

	private BooleanExpression betweenPaymentTotal(BigDecimal min, BigDecimal max) {
		if (min == null && max == null)
			return null;
		if (min != null && max != null)
			return payment.paymentTotal.between(min, max);
		if (min != null)
			return payment.paymentTotal.goe(min);
		return payment.paymentTotal.loe(max);
	}

	private BooleanExpression eqPaymentMethod(String paymentMethod) {
		return (paymentMethod == null || paymentMethod.isEmpty()) ? null : payment.paymentMethod.eq(paymentMethod);
	}

	private BooleanExpression eqPaymentStatus(String paymentStatus) {
		return (paymentStatus == null || paymentStatus.isEmpty()) ? null : payment.paymentStatus.eq(paymentStatus);
	}

	private Predicate isDeleted(Boolean isDeleted) {
		if (isDeleted == null) {
			return null;
		}
		if (isDeleted) {
			return payment.deletedAt.isNotNull();
		}
		return payment.deletedAt.isNull();
	}

	private OrderSpecifier[] createOrderSpecifiers(Sort sort) {
		return sort.stream()
			.filter(order -> "createdAt".equals(order.getProperty())) // "createdAt"만 필터
			.map(order -> new OrderSpecifier(
				order.getDirection().isAscending() ? Order.ASC : Order.DESC,
				payment.createdAt
			))
			.toArray(OrderSpecifier[]::new);
	}

}
