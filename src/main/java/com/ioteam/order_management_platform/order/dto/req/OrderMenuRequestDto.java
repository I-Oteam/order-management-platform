package com.ioteam.order_management_platform.order.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuRequestDto {

	@NotNull
	private UUID orderMenuId;

	@NotNull
	@Min(value = 0)
	private BigDecimal orderPrice;

	@NotNull
	@Min(value = 0)
	private Integer orderCount;
}
