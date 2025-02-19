package com.ioteam.order_management_platform.order.dto.req;


import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.enums.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter

public class ModifyOrderRequestDto {

	@NotNull
	private UUID orderId;
	private String userId;
	private String resId;
	private BigDecimal resTotal;
	private OrderStatus status;
	private OrderType type;
	private String orderLocation;
	private String orderRequest;
}
