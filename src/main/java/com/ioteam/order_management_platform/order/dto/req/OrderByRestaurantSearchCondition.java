package com.ioteam.order_management_platform.order.dto.req;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderByRestaurantSearchCondition {
	private String nickname;
	private LocalDateTime startCreatedAt;
	private LocalDateTime endCreatedAt;
	private BigDecimal minResTotal;
	private BigDecimal maxResTotal;
}
