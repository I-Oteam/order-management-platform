package com.ioteam.order_management_platform.review.entity;

import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.review.dto.req.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_review")
@Entity
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID reviewId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_user_id")
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_res_id")
	private Restaurant restaurant;

	private Integer reviewScore;

	@Column(length = 1000)
	private String reviewContent;

	@Column(length = 100)
	private String reviewImageUrl;

	@ColumnDefault("true")
	private Boolean isPublic;

	private Review(User user, Order order, Restaurant restaurant, Integer reviewScore, String reviewContent,
		String reviewImageUrl, Boolean isPublic) {
		this.user = user;
		this.order = order;
		this.restaurant = restaurant;
		this.reviewScore = reviewScore;
		this.reviewContent = reviewContent;
		this.reviewImageUrl = reviewImageUrl;
		this.isPublic = isPublic;
	}

	public static Review of(User user, Order order, Restaurant restaurant, Integer reviewScore, String reviewContent,
		String reviewImageUrl, Boolean isPublic) {
		return new Review(user, order, restaurant, reviewScore, reviewContent, reviewImageUrl, isPublic);
	}

	public void modify(ModifyReviewRequestDto requestDto) {
		this.reviewScore = requestDto.getReviewScore();
		this.reviewContent = requestDto.getReviewContent();
		this.reviewImageUrl = requestDto.getReviewImageUrl();
		this.isPublic = requestDto.getIsPublic();
	}

	public boolean checkIsAuthor(UUID userId) {
		return this.user.getUserId().equals(userId);
	}

	public boolean checkIsRestaurantOwner(UUID userId) {
		return this.restaurant.getOwner().getUserId().equals(userId);
	}
}
