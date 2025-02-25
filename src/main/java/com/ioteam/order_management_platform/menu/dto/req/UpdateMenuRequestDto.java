package com.ioteam.order_management_platform.menu.dto.req;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.ioteam.order_management_platform.menu.entity.MenuStatus;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateMenuRequestDto {

	@Length(min = 1, max = 100)
	private String rmName;
	@Min(value = 0)
	private BigDecimal rmPrice;
	private String rmImageUrl;
	@Length(max = 100)
	private String rmDescription;
	private MenuStatus rmStatus;
	private Boolean isPublic;
}
