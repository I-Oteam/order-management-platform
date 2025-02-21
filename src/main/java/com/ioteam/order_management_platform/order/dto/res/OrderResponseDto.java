package com.ioteam.order_management_platform.order.dto.res;

import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class OrderResponseDto {

	private UUID orderId;
	private UUID orderUserId;
	private UUID orderResId;
	private BigDecimal orderResTotal;
	private List<OrderMenuResponseDto> orderMenuList;
	private OrderType orderType;
	private OrderStatus orderStatus;
	private String orderLocation;
	private String orderRequest;

	public static OrderResponseDto fromEntity(Order order) {

		return OrderResponseDto.builder()
				.orderId(order.getOrderId())
				.orderUserId(order.getUser().getUserId())
				.orderResId(order.getRestaurant().getResId())
				.orderMenuList(order.getOrderMenus().stream()
						.map(OrderMenuResponseDto::fromEntity)
						.toList())
				.orderResTotal(order.getOrderResTotal())
				.orderType(order.getOrderType())
				.orderStatus(order.getOrderStatus())
				.orderLocation(order.getOrderLocation())
				.orderRequest(order.getOrderRequest())
				.build();
	}
}
