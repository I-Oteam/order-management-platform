package com.ioteam.order_management_platform.review.repository;

import static com.ioteam.order_management_platform.review.entity.QReview.*;
import static com.ioteam.order_management_platform.user.entity.QUser.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.review.dto.AdminReviewResponseDto;
import com.ioteam.order_management_platform.review.dto.ReviewSearchCondition;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.ioteam.order_management_platform.user.entity.User;
import com.querydsl.core.Tuple;
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
	public Page<AdminReviewResponseDto> searchReviewByCondition(ReviewSearchCondition condition, Pageable pageable) {

		List<Tuple> result = queryFactory.select(review, user)
			.from(review)
			.leftJoin(review.user, user)
			.where(
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				eqReviewScore(condition.getScore()),
				isPublic(condition.getIsPublic()),
				isDeleted(condition.getIsDeleted())
			)
			.orderBy(createOrderSpecifiers(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		List<AdminReviewResponseDto> dtoList = result.stream()
			.map(tuple -> {
				Review review1 = tuple.get(review);
				User user1 = tuple.get(user);
				return AdminReviewResponseDto.from(review1, user1);
			})
			.toList();

		JPAQuery<Long> countQuery = queryFactory
			.select(review.count())
			.from(review)
			.leftJoin(review.user)
			.where(
				betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
				eqReviewScore(condition.getScore()),
				isPublic(condition.getIsPublic()),
				isDeleted(condition.getIsDeleted())
			);

		return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetchOne());

	}

	private BooleanExpression eqReviewScore(Integer score) {

		if (score == null)
			return null;
		return review.reviewScore.eq(score);
	}

	private BooleanExpression betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {

		if (startCreatedAt == null || endCreatedAt == null)
			return null;
		if (startCreatedAt.isAfter(endCreatedAt))
			throw new CustomApiException(ReviewException.INVALID_PERIOD);
		return review.createdAt.between(startCreatedAt, endCreatedAt);
	}

	private BooleanExpression isPublic(Boolean isPublic) {

		if (isPublic == null)
			return null;
		return review.isPublic.eq(isPublic);
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
			.filter(order -> List.of("score", "createdAt").contains(order.getProperty()))
			.map(order -> {
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
				switch (order.getProperty()) {
					case "score":
						return new OrderSpecifier(direction, review.reviewScore);
					case "createdAt":
						return new OrderSpecifier(direction, review.createdAt);
				}
				return null;
			})
			.toArray(OrderSpecifier[]::new);
	}
}
