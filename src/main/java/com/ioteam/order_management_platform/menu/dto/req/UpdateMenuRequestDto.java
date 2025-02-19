package com.ioteam.order_management_platform.menu.dto.req;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMenuRequestDto {

	@Length(min = 1, max = 100)
	private String rmName;
	@Min(value = 0)
	private BigDecimal rmPrice;
	private String rmImageUrl;
	@Length(max = 100)
	private String rmDescription;
	private Boolean isPublic;

}
