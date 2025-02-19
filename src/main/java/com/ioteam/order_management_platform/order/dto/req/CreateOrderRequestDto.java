package com.ioteam.order_management_platform.order.dto.req;


import com.ioteam.order_management_platform.order.enums.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDto {

	private String orderUserId;

	private String orderResId;

	@NotNull
	@Min(value = 1, message = "총 주문 금액은 0원 이상이어야 합니다.")
	private BigDecimal orderResTotal;

	private OrderType orderType;

	private String orderLocation;

	private String orderRequest;
}
