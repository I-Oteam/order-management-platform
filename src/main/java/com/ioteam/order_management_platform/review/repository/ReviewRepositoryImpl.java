package com.ioteam.order_management_platform.review.repository;

import static com.ioteam.order_management_platform.restaurant.entity.QRestaurant.*;
import static com.ioteam.order_management_platform.review.entity.QReview.*;
import static com.ioteam.order_management_platform.user.entity.QUser.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.review.dto.req.AdminReviewSearchCondition;
import com.ioteam.order_management_platform.review.dto.res.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewRestaurantResponseDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewUserResponseDto;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<AdminReviewResponseDto> searchReviewAdminByCondition(AdminReviewSearchCondition condition,
		Pageable pageable) {

		List<AdminReviewResponseDto> dtoList = queryFactory.select(review, user.userId, user.username, user.nickname,
				restaurant.resId, restaurant.resName)
			.from(review)
			.leftJoin(user).on(user.userId.eq(review.user.userId))
			.leftJoin(restaurant).on(restaurant.resId.eq(review.restaurant.resId))
			.where(
				eqUserId(condition.getUserId()),
				eqRestaurantId(condition.getRestaurantId()),
				eqReviewScore(condition.getScore()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				isPublic(condition.getIsPublic()),
				isDeleted(condition.getIsDeleted())
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch()
			.stream()
			.map(tuple -> {
				Review review1 = tuple.get(review);
				ReviewUserResponseDto reviewUser = ReviewUserResponseDto.builder()
					.userId(tuple.get(user.userId))
					.username(tuple.get(user.username))
					.nickname(tuple.get(user.nickname))
					.build();
				ReviewRestaurantResponseDto reviewRestaurant = ReviewRestaurantResponseDto.builder()
					.restaurantId(tuple.get(restaurant.resId))
					.restaurantName(tuple.get(restaurant.resName))
					.build();
				return AdminReviewResponseDto.from(review1, reviewUser, reviewRestaurant);
			})
			.toList();

		JPAQuery<Long> countQuery = queryFactory
			.select(review.count())
			.from(review)
			.leftJoin(user).on(user.userId.eq(review.user.userId))
			.leftJoin(restaurant).on(restaurant.resId.eq(review.restaurant.resId))
			.where(
				eqUserId(condition.getUserId()),
				eqRestaurantId(condition.getRestaurantId()),
				eqReviewScore(condition.getScore()),
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				isPublic(condition.getIsPublic()),
				isDeleted(condition.getIsDeleted())
			);
		return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
	}

	@Override
	public Page<ReviewResponseDto> searchReviewByUser(UUID userId, Pageable pageable) {

		List<ReviewResponseDto> dtoList = queryFactory.select(review, user.userId, user.username, user.nickname,
				restaurant.resId, restaurant.resName)
			.from(review)
			.leftJoin(user).on(user.userId.eq(review.user.userId))
			.leftJoin(restaurant).on(restaurant.resId.eq(review.restaurant.resId))
			.where(
				eqUserId(userId),
				review.deletedAt.isNull()
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch()
			.stream()
			.map(tuple -> {
				Review review1 = tuple.get(review);
				ReviewUserResponseDto reviewUser = ReviewUserResponseDto.builder()
					.userId(tuple.get(user.userId))
					.username(tuple.get(user.username))
					.nickname(tuple.get(user.nickname))
					.build();
				ReviewRestaurantResponseDto reviewRestaurant = ReviewRestaurantResponseDto.builder()
					.restaurantId(tuple.get(restaurant.resId))
					.restaurantName(tuple.get(restaurant.resName))
					.build();
				return ReviewResponseDto.from(review1, reviewUser, reviewRestaurant);
			})
			.toList();

		JPAQuery<Long> countQuery = queryFactory
			.select(review.count())
			.from(review)
			.leftJoin(user).on(user.userId.eq(review.user.userId))
			.leftJoin(restaurant).on(restaurant.resId.eq(review.restaurant.resId))
			.where(
				eqUserId(userId),
				review.deletedAt.isNull()
			);

		return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
	}

	@Override
	public Page<ReviewResponseDto> searchReviewByRestaurant(UUID userId, UUID resId, Pageable pageable) {

		// 유저가 가게 오너인지 확인
		boolean isOwner = queryFactory
			.selectFrom(restaurant)
			.where(
				restaurant.resId.eq(resId),
				restaurant.owner.userId.eq(userId)
			)
			.fetchCount() > 0;

		List<ReviewResponseDto> dtoList = queryFactory.select(review, user.userId, user.username, user.nickname,
				restaurant.resId, restaurant.resName)
			.from(review)
			.leftJoin(user).on(user.userId.eq(review.user.userId))
			.leftJoin(restaurant).on(restaurant.resId.eq(review.restaurant.resId))
			.where(
				ifNotOwnerOnlyPublic(isOwner),
				review.restaurant.resId.eq(resId),
				review.deletedAt.isNull()
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch()
			.stream()
			.map(tuple -> {
				Review review1 = tuple.get(review);
				ReviewUserResponseDto reviewUser = ReviewUserResponseDto.builder()
					.userId(tuple.get(user.userId))
					.username(tuple.get(user.username))
					.nickname(tuple.get(user.nickname))
					.build();
				ReviewRestaurantResponseDto reviewRestaurant = ReviewRestaurantResponseDto.builder()
					.restaurantId(tuple.get(restaurant.resId))
					.restaurantName(tuple.get(restaurant.resName))
					.build();
				return ReviewResponseDto.from(review1, reviewUser, reviewRestaurant);
			})
			.toList();

		JPAQuery<Long> countQuery = queryFactory
			.select(review.count())
			.from(review)
			.leftJoin(user).on(user.userId.eq(review.user.userId))
			.leftJoin(restaurant).on(restaurant.resId.eq(review.restaurant.resId))
			.where(
				ifNotOwnerOnlyPublic(isOwner),
				review.restaurant.resId.eq(resId),
				review.deletedAt.isNull()
			);

		return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
	}

	private BooleanExpression ifNotOwnerOnlyPublic(boolean isOwner) {

		return isOwner ? null : review.isPublic.eq(true);
	}

	private BooleanExpression eqReviewScore(Integer score) {

		return score == null ? null : review.reviewScore.eq(score);
	}

	private BooleanExpression eqUserId(UUID userId) {

		return userId == null ? null : review.user.userId.eq(userId);
	}

	private BooleanExpression eqRestaurantId(UUID resId) {

		return resId == null ? null : review.restaurant.resId.eq(resId);
	}

	private BooleanExpression betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {

		if (startCreatedAt == null || endCreatedAt == null)
			return null;
		if (startCreatedAt.isAfter(endCreatedAt))
			throw new CustomApiException(ReviewException.INVALID_PERIOD);
		return review.createdAt.between(startCreatedAt, endCreatedAt);
	}

	private BooleanExpression isPublic(Boolean isPublic) {

		return isPublic == null ? null : review.isPublic.eq(isPublic);
	}

	private BooleanExpression isDeleted(Boolean isDeleted) {

		if (isDeleted == null)
			return null;
		if (isDeleted)
			return review.deletedAt.isNotNull();
		if (!isDeleted)
			return review.deletedAt.isNull();
		return null;
	}

	private OrderSpecifier[] createOrderSpecifiers(Sort sort) {

		return sort.stream()
			.filter(order -> List.of("score", "createdAt", "modifiedAt").contains(order.getProperty()))
			.map(order -> {
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
				switch (order.getProperty()) {
					case "score":
						return new OrderSpecifier(direction, review.reviewScore);
					case "createdAt":
						return new OrderSpecifier(direction, review.createdAt);
					case "modifiedAt":
						return new OrderSpecifier(direction, review.modifiedAt);
				}
				return null;
			})
			.toArray(OrderSpecifier[]::new);
	}
}
