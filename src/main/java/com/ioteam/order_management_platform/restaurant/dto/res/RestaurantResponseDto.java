package com.ioteam.order_management_platform.restaurant.dto.res;

import java.math.BigDecimal;
import java.util.UUID;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
public class RestaurantResponseDto {

	private UUID resId;
	private UUID rcId;
	private String rcName;
	private UUID districtId;
	private String districtName;
	private UUID ownerId;
	private String ownerName;
	private String resName;
	private String resAddress;
	private String resPhone;
	private String resImageUrl;
	private BigDecimal resScore;

	public static RestaurantResponseDto fromRestaurant(Restaurant savedRestaurant) {

		log.info("score : {}", savedRestaurant.getRestaurantScore());
		BigDecimal score =
			(savedRestaurant.getRestaurantScore().getRsScore() != null) ?
				savedRestaurant.getRestaurantScore().getRsScore() :
				BigDecimal.ZERO;

		return RestaurantResponseDto
			.builder()
			.resId(savedRestaurant.getResId())
			.rcId(savedRestaurant.getCategory().getRcId())
			.rcName(savedRestaurant.getCategory().getRcName())
			.districtId(savedRestaurant.getDistrict().getDistrictId())
			.districtName(savedRestaurant.getDistrict().getDistrictSigunguName() + " " + savedRestaurant.getDistrict()
				.getDistrictDongName())
			.ownerId(savedRestaurant.getOwner().getUserId())
			.ownerName(savedRestaurant.getOwner().getUsername())
			.resName(savedRestaurant.getResName())
			.resAddress(savedRestaurant.getResAddress())
			.resPhone(savedRestaurant.getResPhone())
			.resImageUrl(savedRestaurant.getResImageUrl())
			.resScore(score)
			.build();
	}
}
