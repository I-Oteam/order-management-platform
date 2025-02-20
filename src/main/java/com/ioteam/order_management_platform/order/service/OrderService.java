package com.ioteam.order_management_platform.order.service;


import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.order.dto.req.CancelOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.res.OrderListResponseDto;
import com.ioteam.order_management_platform.order.dto.res.OrderResponseDto;
import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.order.enums.OrderStatus;
import com.ioteam.order_management_platform.order.exception.OrderException;
import com.ioteam.order_management_platform.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

	//주문 성공 상태
	public void OrderStatusProcess(UUID orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

		order.orderConfirm();
		orderRepository.save(order);
	}

	//주문 전체 조회하기
	@Transactional
	public OrderListResponseDto getAllOrders() {
		List<Order> orderList = orderRepository.findAll();
		List<OrderResponseDto> responseDtos = orderList.stream()
				.map(OrderResponseDto::fromEntity)
				.collect(Collectors.toList());

		return new OrderListResponseDto(responseDtos);
	}

	//주문 상세 조회하기
	@Transactional
	public OrderResponseDto getOrderDetail(UUID orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

		return OrderResponseDto.fromEntity(order);
	}

	//주문 취소하기
	@Transactional
	public OrderResponseDto cancelOrder(UUID orderId, CancelOrderRequestDto requestDto) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new CustomApiException(OrderException.INVALID_ORDER_ID));

		//5분이 지나면 취소 불가능
		if (order.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
			throw new CustomApiException("취소 가능 시간이 지났습니다.");
		}

		order.orderCancel(requestDto);
		orderRepository.save(order);
		return OrderResponseDto.fromEntity(order);
	}

}


