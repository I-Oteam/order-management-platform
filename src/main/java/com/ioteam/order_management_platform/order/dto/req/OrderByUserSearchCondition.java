package com.ioteam.order_management_platform.order.dto.req;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.enums.OrderType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderByUserSearchCondition {

	private OrderStatus orderStatus;
	private OrderType orderType;
	private LocalDateTime startCreatedAt;
	private LocalDateTime endCreatedAt;
	private BigDecimal minResTotal;
	private BigDecimal maxResTotal;
}

