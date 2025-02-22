package com.ioteam.order_management_platform.restaurant.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.ioteam.order_management_platform.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_restaurant_score")
@Entity
public class RestaurantScore extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID rsId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "rs_res_id")
	private Restaurant restaurant;

	@Column(precision = 2, scale = 1, columnDefinition = "NUMERIC(2,1)")
	private BigDecimal rsScore;

	public RestaurantScore(Restaurant savedRestaurant, BigDecimal zero) {
		this.restaurant = savedRestaurant;
		this.rsScore = zero;
	}
}
