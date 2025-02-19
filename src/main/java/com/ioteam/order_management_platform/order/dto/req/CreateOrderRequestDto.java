package com.ioteam.order_management_platform.order.dto.req;


import com.ioteam.order_management_platform.order.enums.OrderType;
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

	private BigDecimal orderResTotal;

	private OrderType orderType;

	private String orderLocation;

	private String orderRequest;
}
