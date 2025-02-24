package com.ioteam.order_management_platform.review.repository;

import static com.ioteam.order_management_platform.global.utils.QuerydslUtil.*;
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
import com.ioteam.order_management_platform.review.dto.res.QAdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.res.QReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.res.QReviewRestaurantResponseDto;
import com.ioteam.order_management_platform.review.dto.res.QReviewUserResponseDto;
import com.ioteam.order_management_platform.review.dto.res.ReviewResponseDto;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<AdminReviewResponseDto> searchReviewAdminByCondition(AdminReviewSearchCondition condition,
		Pageable pageable) {

		List<AdminReviewResponseDto> dtoList = queryFactory
			.select(new QAdminReviewResponseDto(review.reviewId,
				new QReviewUserResponseDto(user.userId, user.username, user.nickname),
				new QReviewRestaurantResponseDto(restaurant.resId, restaurant.resName),
				review.reviewScore, review.reviewContent, review.reviewImageUrl, review.order.orderId, review.isPublic,
				review.createdAt, review.createdBy, review.modifiedAt, review.modifiedBy, review.deletedAt,
				review.deletedBy))
			.from(review)
			.leftJoin(user).on(user.userId.eq(review.user.userId))
			.leftJoin(restaurant).on(restaurant.resId.eq(review.restaurant.resId))
			.where(
				adminSearchCondition(condition)
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(review.count())
			.from(review)
			.leftJoin(user).on(user.userId.eq(review.user.userId))
			.leftJoin(restaurant).on(restaurant.resId.eq(review.restaurant.resId))
			.where(
				adminSearchCondition(condition)
			);
		return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());
	}

	@Override
	public Page<ReviewResponseDto> searchReviewByUser(UUID userId, Pageable pageable) {

		List<ReviewResponseDto> dtoList = queryFactory
			.select(new QReviewResponseDto(review.reviewId,
				new QReviewUserResponseDto(user.userId, user.username, user.nickname),
				new QReviewRestaurantResponseDto(restaurant.resId, restaurant.resName),
				review.reviewScore, review.reviewContent, review.reviewImageUrl, review.order.orderId, review.isPublic,
				review.createdAt)
			)
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
			.fetch();

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

		List<ReviewResponseDto> dtoList = queryFactory
			.select(new QReviewResponseDto(review.reviewId,
				new QReviewUserResponseDto(user.userId, user.username, user.nickname),
				new QReviewRestaurantResponseDto(restaurant.resId, restaurant.resName),
				review.reviewScore, review.reviewContent, review.reviewImageUrl, review.order.orderId, review.isPublic,
				review.createdAt)
			)
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
			.fetch();

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

	private BooleanBuilder adminSearchCondition(AdminReviewSearchCondition condition) {
		return eqUserId(condition.getUserId())
			.and(eqRestaurantId(condition.getRestaurantId()))
			.and(eqReviewScore(condition.getScore()))
			.and(betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()))
			.and(isPublic(condition.getIsPublic()))
			.and(isDeleted(condition.getIsDeleted()));
	}

	private BooleanBuilder ifNotOwnerOnlyPublic(boolean isOwner) {
		return isOwner ? new BooleanBuilder() : new BooleanBuilder(review.isPublic.eq(true));
	}

	private BooleanBuilder eqReviewScore(Integer score) {
		return nullSafeBuilder(() -> review.reviewScore.eq(score));
	}

	private BooleanBuilder eqUserId(UUID userId) {
		return nullSafeBuilder(() -> review.user.userId.eq(userId));
	}

	private BooleanBuilder eqRestaurantId(UUID resId) {
		return nullSafeBuilder(() -> review.restaurant.resId.eq(resId));
	}

	private BooleanBuilder betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {

		if (startCreatedAt == null || endCreatedAt == null)
			return new BooleanBuilder();
		if (startCreatedAt.isAfter(endCreatedAt))
			throw new CustomApiException(ReviewException.INVALID_PERIOD);
		return new BooleanBuilder(review.createdAt.between(startCreatedAt, endCreatedAt));
	}

	private BooleanBuilder isPublic(Boolean isPublic) {
		return nullSafeBuilder(() -> review.isPublic.eq(isPublic));
	}

	private BooleanBuilder isDeleted(Boolean isDeleted) {
		if (isDeleted == null)
			return new BooleanBuilder();
		if (isDeleted)
			return new BooleanBuilder(review.deletedAt.isNotNull());
		if (!isDeleted)
			return new BooleanBuilder(review.deletedAt.isNull());
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
