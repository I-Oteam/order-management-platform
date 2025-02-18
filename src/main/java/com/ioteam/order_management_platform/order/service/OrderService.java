package com.ioteam.order_management_platform.order.service;


import com.ioteam.order_management_platform.order.dto.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.OrderResponseDto;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

	private final OrderRepository orderRepository;

	//주문 생성하기
	@Transactional
	public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
		Order order = Order.builder()
				.orderUserId(requestDto.getOrderUserId())
				.orderResId(requestDto.getOrderResId())
				.orderType(requestDto.getOrderType())
				.orderLocation(requestDto.getOrderLocation())
				.orderRequest(requestDto.getOrderRequest())
				.orderResTotal(requestDto.getOrderResTotal() != null ? requestDto.getOrderResTotal() : BigDecimal.ZERO)
				.orderStatus(OrderStatus.대기)
				.build();

		Order savedOrder = orderRepository.save(order);
		return OrderResponseDto.fromEntity(savedOrder);
	}

}


