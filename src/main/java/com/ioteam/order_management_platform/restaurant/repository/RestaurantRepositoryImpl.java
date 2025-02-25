package com.ioteam.order_management_platform.restaurant.repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.ioteam.order_management_platform.restaurant.entity.QRestaurant;
import com.ioteam.order_management_platform.restaurant.entity.QRestaurantScore;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Restaurant> searchRestaurants(BigDecimal score, Pageable pageable) {
		QRestaurant restaurant = QRestaurant.restaurant;
		QRestaurantScore restaurantScore = QRestaurantScore.restaurantScore;

		BooleanBuilder builder = new BooleanBuilder();

		builder.and(restaurant.deletedAt.isNull());

		if (score != null) {
			BigDecimal minScore = score.setScale(1, RoundingMode.FLOOR);
			BigDecimal maxScore = minScore.add(BigDecimal.valueOf(0.9));

			// 새로생긴 가게의 경우 score이 null인경우
			builder.and(
				Expressions.numberTemplate(BigDecimal.class, "coalesce({0}, {1})", restaurant.restaurantScore.rsScore,
						BigDecimal.ZERO)
					.goe(minScore)
					.and(Expressions.numberTemplate(BigDecimal.class, "coalesce({0}, {1})",
						restaurant.restaurantScore.rsScore, BigDecimal.ZERO).lt(maxScore))
			);
		}

		List<Restaurant> results = queryFactory
			.selectFrom(restaurant)
			.leftJoin(restaurant.restaurantScore, restaurantScore)
			.fetchJoin()
			.where(builder)
			.orderBy(restaurantScore.rsScore.desc().nullsFirst())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = Optional.ofNullable(queryFactory
			.select(restaurant.count())
			.from(restaurant)
			.leftJoin(restaurant.restaurantScore, restaurantScore)
			.where(builder)
			.fetchOne()).orElse(0L);

		log.info("검색 결과 : {}", results.size());

		return new PageImpl<>(results, pageable, total);
	}
}
