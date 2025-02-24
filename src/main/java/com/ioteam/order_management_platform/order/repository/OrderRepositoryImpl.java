package com.ioteam.order_management_platform.order.repository;

import static com.ioteam.order_management_platform.global.utils.QuerydslUtil.*;
import static com.ioteam.order_management_platform.order.entity.QOrder.*;
import static com.ioteam.order_management_platform.order.entity.QOrderMenu.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.order.dto.req.AdminOrderSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.OrderByRestaurantSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.OrderByUserSearchCondition;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.enums.OrderType;
import com.ioteam.order_management_platform.payment.exception.PaymentException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Order> searchOrderByRestaurantAndCondition(UUID resId,
		OrderByRestaurantSearchCondition condition,
		Pageable pageable) {

		OrderSpecifier[] orderSpecifiers = createOrderSpecifiers(pageable.getSort());

		// 1. 페이지네이션 + 조건에 맞는 order Id 조회
		List<UUID> orderIds = queryFactory
			.select(order.orderId)
			.from(order)
			.where(
				order.restaurant.resId.eq(resId),
				byResSearch(condition)
			)
			.orderBy(orderSpecifiers)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		if (orderIds.isEmpty()) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		// 2. 주문 아이디 in 절로 묶어 페치조인 조회
		List<Order> orders = queryFactory
			.selectFrom(order).distinct()
			.leftJoin(order.orderMenus, orderMenu).fetchJoin()
			.leftJoin(orderMenu.menu).fetchJoin()
			.leftJoin(order.restaurant).fetchJoin()
			.where(
				order.orderId.in(orderIds)
			)
			.orderBy(orderSpecifiers)
			.fetch();

		// 3. 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(order.count())
			.from(order)
			.where(
				order.restaurant.resId.eq(resId),
				byResSearch(condition)
			);

		return PageableExecutionUtils.getPage(orders, pageable, () -> countQuery.fetchOne());
	}

	@Override
	public Page<Order> searchOrderByUserAndCondition(UUID userId,
		OrderByUserSearchCondition condition,
		Pageable pageable) {

		OrderSpecifier[] orderSpecifiers = createOrderSpecifiers(pageable.getSort());

		// 1. 페이지네이션 + 조건에 맞는 order Id 조회
		List<UUID> orderIds = queryFactory
			.select(order.orderId)
			.from(order)
			.where(
				order.user.userId.eq(userId), //사용자의 주문만 조회
				byUserSearch(condition)
			)
			.orderBy(orderSpecifiers)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		if (orderIds.isEmpty()) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		// 2. 주문 ID 기반으로 페치조인 조회
		List<Order> orders = queryFactory
			.selectFrom(order).distinct()
			.leftJoin(order.orderMenus, orderMenu).fetchJoin()
			.leftJoin(orderMenu.menu).fetchJoin()
			.leftJoin(order.restaurant).fetchJoin()
			.where(
				order.orderId.in(orderIds)
			)
			.orderBy(orderSpecifiers)
			.fetch();

		// 3. 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(order.count())
			.from(order)
			.where(
				order.user.userId.eq(userId),
				byUserSearch(condition)
			);

		return PageableExecutionUtils.getPage(orders, pageable, () -> countQuery.fetchOne());
	}

	@Override
	public Page<Order> searchOrderAdminByCondition(AdminOrderSearchCondition condition, Pageable pageable) {
		OrderSpecifier[] orderSpecifiers = createOrderSpecifiers(pageable.getSort());

		// 1. 검색 조건 + 페이징에 맞는 주문 ID 목록 조회
		List<UUID> orderIds = queryFactory
			.select(order.orderId)
			.from(order)
			.where(
				byAdminSearch(condition)
			)
			.orderBy(orderSpecifiers)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		if (orderIds.isEmpty()) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		// 2. 조회된 주문 ID를 기준으로 주문 및 연관 데이터를 페치조인하여 조회
		List<Order> orders = queryFactory
			.selectFrom(order).distinct()
			.leftJoin(order.orderMenus, orderMenu).fetchJoin()
			.leftJoin(orderMenu.menu).fetchJoin()
			.leftJoin(order.restaurant).fetchJoin()
			.leftJoin(order.user).fetchJoin()
			.where(order.orderId.in(orderIds))
			.orderBy(orderSpecifiers)
			.fetch();

		// 3. 전체 개수를 조회하는 카운트 쿼리
		JPAQuery<Long> countQuery = queryFactory
			.select(order.count())
			.from(order)
			.where(
				byAdminSearch(condition)
			);

		return PageableExecutionUtils.getPage(orders, pageable, countQuery::fetchOne);
	}

	private BooleanBuilder byAdminSearch(AdminOrderSearchCondition condition) {

		return eqRestaurantId(condition.getRestaurantId())
			.and(eqRestaurantName(condition.getRestaurantName()))
			.and(eqUsername(condition.getUsername()))
			.and(eqNickname(condition.getNickname()))
			.and(eqOrderStatus(condition.getOrderStatus()))
			.and(eqOrderType(condition.getOrderType()))
			.and(betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()))
			.and(betweenResTotal(condition.getMinResTotal(), condition.getMaxResTotal()));
	}

	private BooleanBuilder byResSearch(OrderByRestaurantSearchCondition condition) {
		return eqNickname(condition.getNickname())
			.and(eqOrderStatus(condition.getOrderStatus()))
			.and(eqOrderType(condition.getOrderType()))
			.and(betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()))
			.and(betweenResTotal(condition.getMinResTotal(), condition.getMaxResTotal()));
	}

	private BooleanBuilder byUserSearch(OrderByUserSearchCondition condition) {
		return eqRestaurantName(condition.getRestaurantName())
			.and(eqOrderStatus(condition.getOrderStatus()))
			.and(eqOrderType(condition.getOrderType()))
			.and(betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()))
			.and(betweenResTotal(condition.getMinResTotal(), condition.getMaxResTotal()));
	}

	private BooleanBuilder eqRestaurantId(UUID restaurantId) {
		return nullSafeBuilder(() -> order.restaurant.resId.eq(restaurantId));
	}

	private BooleanBuilder eqUsername(String username) {
		return nullSafeBuilder(() -> order.user.username.eq(username));
	}

	private BooleanBuilder eqRestaurantName(String restaurantName) {
		return nullSafeBuilder(() -> order.restaurant.resName.eq(restaurantName));
	}

	private BooleanBuilder eqOrderType(OrderType orderType) {
		return nullSafeBuilder(() -> order.orderType.eq(orderType));
	}

	private BooleanBuilder eqOrderStatus(OrderStatus orderStatus) {
		return nullSafeBuilder(() -> order.orderStatus.eq(orderStatus));
	}

	private BooleanBuilder eqNickname(String nickname) {
		return nullSafeBuilder(() -> order.user.nickname.eq(nickname));
	}

	private BooleanBuilder betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {
		if (startCreatedAt == null || endCreatedAt == null)
			return new BooleanBuilder();
		if (startCreatedAt.isAfter(endCreatedAt))
			throw new CustomApiException(PaymentException.INVALID_PERIOD);
		return new BooleanBuilder(order.createdAt.between(startCreatedAt, endCreatedAt));
	}

	private BooleanBuilder betweenResTotal(BigDecimal min, BigDecimal max) {
		if (min == null && max == null)
			return new BooleanBuilder();
		if (min != null && max != null)
			return new BooleanBuilder(order.orderResTotal.between(min, max));
		if (min != null)
			return new BooleanBuilder(order.orderResTotal.goe(min));
		return new BooleanBuilder(order.orderResTotal.loe(max));
	}

	private OrderSpecifier[] createOrderSpecifiers(Sort sorts) {

		return sorts.stream()
			.filter(sort -> List.of("orderResTotal", "createdAt", "modifiedAt").contains(sort.getProperty()))
			.map(sort -> {
				com.querydsl.core.types.Order direction =
					sort.getDirection().isAscending() ? com.querydsl.core.types.Order.ASC :
						com.querydsl.core.types.Order.DESC;
				switch (sort.getProperty()) {
					case "orderResTotal":
						return new OrderSpecifier(direction, order.orderResTotal);
					case "createdAt":
						return new OrderSpecifier(direction, order.createdAt);
					case "modifiedAt":
						return new OrderSpecifier(direction, order.modifiedAt);
				}
				return null;
			})
			.toArray(OrderSpecifier[]::new);
	}
}
