package com.ioteam.order_management_platform.review.dto.req;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.review.entity.Review;
import com.ioteam.order_management_platform.user.entity.User;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CreateReviewRequestDto {
	@NotNull
	private UUID orderId;
	@NotNull
	private UUID restaurantId;
	@NotNull
	@Min(0)
	@Max(5)
	private Integer reviewScore;
	@NotBlank
	@Length(max = 1000)
	private String reviewContent;
	@Length(max = 100)
	private String reviewImageUrl;
	@NotNull
	private Boolean isPublic;

	public Review toEntity(User referenceUser, Order order, Restaurant referenceRestaurant) {
		return Review.builder()
			.user(referenceUser)
			.order(order)
			.restaurant(referenceRestaurant)
			.reviewScore(this.reviewScore)
			.reviewContent(this.reviewContent)
			.reviewImageUrl(this.reviewImageUrl)
			.isPublic(this.isPublic)
			.build();
	}
}
