package com.ioteam.order_management_platform.restaurant.dto.req;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyRestaurantRequestDto {

	private UUID resOwnerId;
	private UUID rcId;
	private UUID resDistrictId;
	@Length(max = 100)
	private String resName;
	@Length(max = 100)
	private String resAddress;
	@Length(max = 20)
	private String resPhone;
	private String resImageUrl;
}
