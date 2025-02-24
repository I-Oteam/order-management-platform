package com.ioteam.order_management_platform.menu.repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.ioteam.order_management_platform.menu.dto.res.MenuResponseDto;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.entity.MenuStatus;
import com.ioteam.order_management_platform.menu.entity.QMenu;
import com.ioteam.order_management_platform.restaurant.entity.QRestaurant;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MenuCustomRepositoryImpl implements MenuCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<MenuResponseDto> findMenusByResIdAndRole(UUID resId, UserDetailsImpl userDetails, Pageable pageable) {
		QMenu menu = QMenu.menu;
		UserRoleEnum role = userDetails.getRole();
		UUID userId = userDetails.getUserId();
		boolean isOwner = isOwner(userId, resId);

		BooleanExpression condition = menu.restaurant.resId.eq(resId).and(menu.deletedAt.isNull());

		// role과 OWNER id 일치 여부에 따른 condition 추가
		if (role.equals(UserRoleEnum.CUSTOMER) || (role.equals(UserRoleEnum.OWNER) && !isOwner)) {
			condition = condition.and(menu.rmStatus.ne(MenuStatus.HIDDEN));
		}

		// 정렬 조건 변환
		List<OrderSpecifier> orders = orderCondition(menu, pageable);

		long total = jpaQueryFactory
			.selectFrom(menu)
			.where(condition)
			.fetchCount();

		List<Menu> menus = jpaQueryFactory
			.selectFrom(menu)
			.where(condition)
			.orderBy(orders.toArray(new OrderSpecifier[0]))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		List<MenuResponseDto> content = menus.stream()
			.map(MenuResponseDto::fromEntity)
			.collect(Collectors.toList());

		return PageableExecutionUtils.getPage(content, pageable, () -> total);
	}

	public boolean isOwner(UUID userId, UUID resId) {
		QRestaurant restaurant = QRestaurant.restaurant;
		log.info("Checking if restaurant is owner of user {}", userId);
		Integer owner = jpaQueryFactory.selectOne()
			.from(restaurant)
			.where(restaurant.resId.eq(resId).and(restaurant.owner.userId.eq(userId)))
			.fetchFirst();
		return owner != null;
	}

	private List<OrderSpecifier> orderCondition(QMenu menu, Pageable pageable) {
		return pageable.getSort().stream()
			.map(order -> {
				Order direction = order.isAscending() ? Order.ASC : Order.DESC;
				switch (order.getProperty()) {
					case "createdAt":
						return new OrderSpecifier(direction, menu.createdAt);
					case "modifiedAt":
						return new OrderSpecifier(direction, menu.modifiedAt);
					case "rmPrice":
						return new OrderSpecifier(direction, menu.rmPrice);
					default:
						return new OrderSpecifier(direction, menu.createdAt);
				}
			})
			.collect(Collectors.toList());
	}

}
