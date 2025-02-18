package com.ioteam.order_management_platform.order.controller;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.order.dto.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.OrderResponseDto;
import com.ioteam.order_management_platform.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "주문", description = "주문 API")
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/orders")
	public ResponseEntity<CommonResponse<OrderResponseDto>> createOrder(@RequestBody CreateOrderRequestDto requestDto) {

		OrderResponseDto responseDto = orderService.createOrder(requestDto);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/orders/" + responseDto.getOrderId().toString())
				.build()
				.toUri();

		return ResponseEntity.created(location)
				.body(new CommonResponse<>(
						"주문이 성공적으로 생성되었습니다.",
						responseDto));
	}

}
