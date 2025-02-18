package com.ioteam.order_management_platform.review.entity;

import java.time.LocalDateTime;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

	private int reviewScore;

	@Column(length = 1000)
	private String reviewContent;

	@Column(length = 100)
	private String reviewImageUrl;

	@ColumnDefault("true")
	private Boolean isPublic;

	private LocalDateTime deletedAt;

	private UUID deletedBy;

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = UUID.randomUUID();
	}

	public void modify(ModifyReviewRequestDto requestDto) {
		this.reviewScore = requestDto.getReviewScore();
		this.reviewContent = requestDto.getReviewContent();
		this.reviewImageUrl = requestDto.getReviewImageUrl();
		this.isPublic = requestDto.getIsPublic();
	}
}
