package com.ioteam.order_management_platform.user.repository;

import static com.ioteam.order_management_platform.user.entity.QUser.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.user.dto.req.UserSearchCondition;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.exception.UserException;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<User> searchUserByCondition(UserSearchCondition condition, Pageable pageable) {
		List<User> content = queryFactory.select(user)
			.from(user)
			.where(
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				eqRole(condition.getRole()),
				isDeleted(condition.getIsDeleted())
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(user.count())
			.from(user)
			.where(
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				eqRole(condition.getRole()),
				isDeleted(condition.getIsDeleted())
			);

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	private BooleanExpression betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {
		if (startCreatedAt == null || endCreatedAt == null)
			return null;
		if (startCreatedAt.isAfter(endCreatedAt))
			throw new CustomApiException(UserException.INVALID_PERIOD);
		return user.createdAt.between(startCreatedAt, endCreatedAt);
	}

	private BooleanExpression eqRole(UserRoleEnum role) {
		if (role == null)
			return null;
		return user.role.eq(role);
	}

	private BooleanExpression isDeleted(Boolean isDeleted) {
		if (isDeleted == null)
			return null;
		if (isDeleted)
			return user.deletedAt.isNotNull();
		return user.deletedAt.isNull();
	}

	private OrderSpecifier[] createOrderSpecifiers(Sort sort) {
		return sort.stream()
			.filter(order -> List.of("role", "createdAt", "modifiedAt").contains(order.getProperty()))
			.map(order -> {
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
				return switch (order.getProperty()) {
					case "createdAt" -> new OrderSpecifier<>(direction, user.createdAt);
					case "role" -> new OrderSpecifier<>(direction, user.role);
					case "modifiedAt" -> new OrderSpecifier<>(direction, user.modifiedAt);
					default -> null;
				};
			})
			.toArray(OrderSpecifier[]::new);
	}
}
