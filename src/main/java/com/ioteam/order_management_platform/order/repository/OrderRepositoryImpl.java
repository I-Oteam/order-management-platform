package com.ioteam.order_management_platform.order.repository;

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
import com.ioteam.order_management_platform.order.dto.req.OrderByRestaurantSearchCondition;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.payment.exception.PaymentException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
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
				eqNickname(condition.getNickname()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenResTotal(condition.getMinResTotal(), condition.getMaxResTotal())
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
				eqNickname(condition.getNickname()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				betweenResTotal(condition.getMinResTotal(), condition.getMaxResTotal())
			);

		return PageableExecutionUtils.getPage(orders, pageable, () -> countQuery.fetchOne());
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
		return order.createdAt.between(startCreatedAt, endCreatedAt);
	}

	private BooleanExpression betweenResTotal(BigDecimal min, BigDecimal max) {
		if (min == null && max == null)
			return null;
		if (min != null && max != null)
			return order.orderResTotal.between(min, max);
		if (min != null)
			return order.orderResTotal.goe(min);
		return order.orderResTotal.loe(max);
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
