package com.ioteam.order_management_platform.order.dto;

import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class OrderResponseDto {

	private UUID orderId;
	private String orderUserId;
	private String orderResId;
	private BigDecimal orderResTotal;
	private OrderType orderType;
	private OrderStatus orderStatus;
	private String orderLocation;
	private String orderRequest;

	public static OrderResponseDto fromEntity(Order order) {

		System.out.println("Order ID Type: " + order.getOrderId().getClass().getName());
		return OrderResponseDto.builder()
				.orderId(order.getOrderId())
				.orderUserId(order.getOrderUserId())
				.orderResId(order.getOrderResId())
				.orderResTotal(order.getOrderResTotal())
				.orderType(order.getOrderType())
				.orderStatus(order.getOrderStatus())
				.orderLocation(order.getOrderLocation())
				.orderRequest(order.getOrderRequest())
				.build();
	}
}
