package com.ioteam.order_management_platform.order.service;


import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.order.dto.req.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.res.OrderListResponseDto;
import com.ioteam.order_management_platform.order.dto.res.OrderResponseDto;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.exception.OrderException;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
				.orderResTotal(requestDto.getOrderResTotal())
				.orderStatus(OrderStatus.WAITING)
				.build();

		Order savedOrder = orderRepository.save(order);
		return OrderResponseDto.fromEntity(savedOrder);
	}

	//주문 전체 조회하기
	public OrderListResponseDto getAllOrders() {
		List<Order> orderList = orderRepository.findAll();
		List<OrderResponseDto> responseDtos = orderList.stream()
				.map(OrderResponseDto::fromEntity)
				.collect(Collectors.toList());

		return new OrderListResponseDto(responseDtos);
	}

	//주문 상세 조회하기
	public OrderResponseDto getOrderDetail(UUID orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

//		List<Order> orderList = orderRepository.findByOrder_OrderId(orderId);
		return OrderResponseDto.fromEntity(order);
	}

}


