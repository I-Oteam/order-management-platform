package com.ioteam.order_management_platform.restaurant.dto.res;

import java.util.UUID;

import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

	public static RestaurantResponseDto fromRestaurant(Restaurant savedRestaurant) {
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
			.build();
	}
}
