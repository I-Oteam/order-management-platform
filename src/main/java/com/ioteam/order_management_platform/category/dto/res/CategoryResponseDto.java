package com.ioteam.order_management_platform.category.dto.res;

import java.util.UUID;

import com.ioteam.order_management_platform.category.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponseDto {

	private UUID rcId;
	private String rcName;

	public static CategoryResponseDto fromCategory(Category category) {
		return CategoryResponseDto
			.builder()
			.rcId(category.getRcId())
			.rcName(category.getRcName())
			.build();
	}

}
