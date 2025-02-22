package com.ioteam.order_management_platform.menu.repository;

import java.util.List;
import java.util.UUID;

import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.entity.MenuStatus;
import com.ioteam.order_management_platform.menu.entity.QMenu;
import com.ioteam.order_management_platform.restaurant.entity.QRestaurant;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MenuCustomRepositoryImpl implements MenuCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Menu> findMenusByResIdAndRole(UUID resId, UserDetailsImpl userDetails) {
		QMenu menu = QMenu.menu;
		UserRoleEnum role = userDetails.getRole();
		UUID userId = userDetails.getUserId();
		boolean isOwner = isOwner(userId, resId);

		BooleanExpression condition = menu.restaurant.resId.eq(resId).and(menu.deletedAt.isNull());

		// role과 OWNER id 일치 여부에 따른 condition 추가
		if (role.equals(UserRoleEnum.CUSTOMER) || (role.equals(UserRoleEnum.OWNER) && !isOwner)) {
			condition = condition.and(menu.rmStatus.ne(MenuStatus.HIDDEN));
		}

		return jpaQueryFactory.selectFrom(menu).where(condition).fetch();
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

}
