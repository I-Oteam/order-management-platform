package com.ioteam.order_management_platform.payment.repository;

import static com.ioteam.order_management_platform.order.entity.QOrder.*;
import static com.ioteam.order_management_platform.payment.entity.QPayment.*;
import static com.ioteam.order_management_platform.restaurant.entity.QRestaurant.*;
import static com.ioteam.order_management_platform.user.entity.QUser.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.payment.dto.req.AdminPaymentSearchCondition;
import com.ioteam.order_management_platform.payment.dto.req.CustomerPaymentSearchCondition;
import com.ioteam.order_management_platform.payment.dto.req.OwnerPaymentSearchCondition;
import com.ioteam.order_management_platform.payment.dto.res.AdminPaymentResponseDto;
import com.ioteam.order_management_platform.payment.dto.res.PaymentResponseDto;
import com.ioteam.order_management_platform.payment.entity.PaymentStatusEnum;
import com.ioteam.order_management_platform.payment.exception.PaymentException;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<AdminPaymentResponseDto> searchPaymentAdminByCondition(AdminPaymentSearchCondition condition,
		Pageable pageable) {
		List<AdminPaymentResponseDto> dtoList = queryFactory
			.select(payment)
			.from(payment)
			.join(payment.order, order).fetchJoin()
			.join(order.user, user).fetchJoin()
			.join(order.restaurant, restaurant).fetchJoin()
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
			.fetch()
			.stream()
			.map(AdminPaymentResponseDto::from) // DTO 변환을 from() 메서드에서 처리
			.toList();

		JPAQuery<Long> countQuery = queryFactory
			.select(payment.count())
			.from(payment)
			.join(payment.order, order)
			.where(
				eqOrderId(condition.getOrderId()),
				eqCustomerId(condition.getCustomerId()),
				eqRestaurantId(condition.getRestaurantId()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenPaymentTotal(condition.getMinPaymentTotal(), condition.getMaxPaymentTotal()),
				eqPaymentMethod(condition.getPaymentMethod()),
				eqPaymentStatus(condition.getPaymentStatus()),
				isDeleted(condition.getIsDeleted())
			);

		return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
	}

	@Override
	public Page<PaymentResponseDto> searchPaymentByUser(CustomerPaymentSearchCondition condition, UUID userId,
		Pageable pageable) {
		List<PaymentResponseDto> dtoList = queryFactory
			.select(payment)
			.from(payment)
			.join(payment.order, order).fetchJoin()
			.join(order.restaurant, restaurant).fetchJoin()
			.where(
				order.user.userId.eq(userId),
				eqRestaurantName(condition.getRestaurantName()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenPaymentTotal(condition.getMinPaymentTotal(), condition.getMaxPaymentTotal()),
				eqPaymentMethod(condition.getPaymentMethod()),
				eqPaymentStatus(condition.getPaymentStatus()),
				payment.deletedAt.isNull()
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch()
			.stream()
			.map(PaymentResponseDto::from)
			.toList();

		JPAQuery<Long> countQuery = queryFactory
			.select(payment.count())
			.from(payment)
			.join(payment.order, order)
			.where(
				order.user.userId.eq(userId),
				eqRestaurantName(condition.getRestaurantName()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenPaymentTotal(condition.getMinPaymentTotal(), condition.getMaxPaymentTotal()),
				eqPaymentMethod(condition.getPaymentMethod()),
				eqPaymentStatus(condition.getPaymentStatus()),
				payment.deletedAt.isNull()
			);

		return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
	}

	@Override
	public Page<PaymentResponseDto> searchPaymentByRestaurant(OwnerPaymentSearchCondition condition,
		UUID restaurantId, Pageable pageable) {
		List<PaymentResponseDto> dtoList = queryFactory
			.select(payment)
			.from(payment)
			.join(payment.order, order).fetchJoin()
			.join(order.user, user).fetchJoin()
			.join(order.restaurant, restaurant).fetchJoin()
			.where(
				order.restaurant.resId.eq(restaurantId),
				eqNickname(condition.getNickname()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenPaymentTotal(condition.getMinPaymentTotal(), condition.getMaxPaymentTotal()),
				eqPaymentStatus(condition.getPaymentStatus()),
				payment.deletedAt.isNull()
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch()
			.stream()
			.map(PaymentResponseDto::from)
			.toList();

		JPAQuery<Long> countQuery = queryFactory
			.select(payment.count())
			.from(payment)
			.join(payment.order, order)
			.where(
				order.restaurant.resId.eq(restaurantId),
				eqNickname(condition.getNickname()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenPaymentTotal(condition.getMinPaymentTotal(), condition.getMaxPaymentTotal()),
				eqPaymentStatus(condition.getPaymentStatus()),
				payment.deletedAt.isNull()
			);

		return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
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

	private BooleanExpression eqRestaurantName(String restaurantName) {
		return restaurantName == null ? null : order.restaurant.resName.eq(restaurantName);
	}

	private BooleanExpression eqNickname(String nickname) {
		return nickname == null ? null : order.user.nickname.eq(nickname);
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

	private BooleanExpression eqPaymentStatus(PaymentStatusEnum paymentStatus) {
		return (paymentStatus == null) ? null : payment.paymentStatus.eq(paymentStatus);
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
