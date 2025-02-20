package com.ioteam.order_management_platform.order.dto.res;

import com.ioteam.order_management_platform.order.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class OrderMenuResponseDto {

	private UUID orderMenuId;

	private UUID orderRmId;

	private String orderRmName;

	private BigDecimal orderPrice;

	private Integer orderCount;

	public static OrderMenuResponseDto fromEntity(OrderMenu orderMenu) {
		return OrderMenuResponseDto.builder()
				.orderMenuId(orderMenu.getOrderMenuId())
				.orderRmId(orderMenu.getMenu().getRmId())
				.orderRmName(orderMenu.getMenu().getRmName())
				.orderPrice(orderMenu.getOrderPrice())
				.orderCount(orderMenu.getOrderCount())
				.build();
	}
}
