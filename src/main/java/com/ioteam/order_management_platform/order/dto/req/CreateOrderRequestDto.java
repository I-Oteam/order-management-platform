package com.ioteam.order_management_platform.order.dto.req;


import com.ioteam.order_management_platform.order.enums.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDto {

	@NotNull
	private UUID orderResId;

	private List<OrderMenuRequestDto> orderMenuList = new ArrayList<>();

	@NotNull
	@Min(value = 0, message = "총 주문 금액은 0원 이상이어야 합니다.")
	private BigDecimal orderResTotal;

	private OrderType orderType;

	private String orderLocation;

	private String orderRequest;
}
