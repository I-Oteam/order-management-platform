package com.ioteam.order_management_platform.review.repository;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.review.dto.ReviewSearchCondition;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.review.exception.ReviewException;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ioteam.order_management_platform.review.entity.QReview.review;


@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> searchReviewByCondition(ReviewSearchCondition condition, Pageable pageable) {

        List<Review> content = queryFactory.select(review)
                .from(review)
                .where(
                        betweenPeriod(condition.getStartCreatedAt(), condition.getEndCreatedAt()),
                        reviewScoreEq(condition.getScore())
                )
                .orderBy(createOrderSpecifiers(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        betweenPeriod(condition.getEndCreatedAt(), condition.getEndCreatedAt()),
                        reviewScoreEq(condition.getScore())
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());

    }

    private BooleanExpression reviewScoreEq(Integer score) {

        if (score == null) return null;
        return review.reviewScore.eq(score);
    }

    private BooleanExpression betweenPeriod(LocalDateTime startCreatedAt, LocalDateTime endCreatedAt) {

        if (startCreatedAt == null || endCreatedAt == null)  return null;
        if (startCreatedAt.isAfter(endCreatedAt)) throw new CustomApiException(ReviewException.INVALID_PERIOD);
        return review.createdAt.between(startCreatedAt, endCreatedAt);
    }

    private OrderSpecifier[] createOrderSpecifiers(Sort sort) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        // 생성 시간, 별점 기준으로만 정렬
        for(Sort.Order order : sort) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            switch(order.getProperty()) {
                case "score":
                    orderSpecifiers.add(new OrderSpecifier(direction, review.reviewScore));
                case "createdAt":
                    orderSpecifiers.add(new OrderSpecifier(direction, review.createdAt));
            }
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}
