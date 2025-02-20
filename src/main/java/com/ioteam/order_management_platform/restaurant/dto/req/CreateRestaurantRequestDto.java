package com.ioteam.order_management_platform.restaurant.dto.req;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.district.entity.District;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.user.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateRestaurantRequestDto {

	@NotNull
	private UUID resOwnerId;

	@NotNull
	private UUID rcId;

	@NotNull
	private UUID resDistrictId;

	@NotNull
	@Length(min = 1, max = 100)
	private String resName;

	@NotNull
	@Length(min = 1, max = 100)
	private String resAddress;

	@NotNull
	@Length()
	private String resPhone;

	private String resImageUrl;

	public static Restaurant toCategory(
		CreateRestaurantRequestDto createRestaurantRequestDto,
		Category category,
		User user,
		District district
	) {
		return Restaurant
			.builder()
			.owner(user)
			.category(category)
			.district(district)
			.resName(createRestaurantRequestDto.getResName())
			.resAddress(createRestaurantRequestDto.getResAddress())
			.resPhone(createRestaurantRequestDto.getResPhone())
			.resImageUrl(createRestaurantRequestDto.getResImageUrl())
			.build();
	}
}
