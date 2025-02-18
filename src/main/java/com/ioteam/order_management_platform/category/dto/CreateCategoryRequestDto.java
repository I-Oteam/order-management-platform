package com.ioteam.order_management_platform.category.dto;

import org.hibernate.validator.constraints.Length;

import com.ioteam.order_management_platform.category.entity.Category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateCategoryRequestDto {

	@NotNull
	@Length(max = 100)
	private String rcName;

	public Category toCategory(CreateCategoryRequestDto categoryRequestDto) {
		return Category.builder()
			.rcName(categoryRequestDto.getRcName())
			.build();
	}
}
