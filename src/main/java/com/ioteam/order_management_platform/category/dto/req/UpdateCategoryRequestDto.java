package com.ioteam.order_management_platform.category.dto.req;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UpdateCategoryRequestDto {

	@NotNull
	@Length(min = 1, max = 100)
	private String rcName;

}
