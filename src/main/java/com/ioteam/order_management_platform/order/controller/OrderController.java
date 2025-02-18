package com.ioteam.order_management_platform.order.controller;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.order.dto.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.OrderListResponseDto;
import com.ioteam.order_management_platform.order.dto.OrderResponseDto;
import com.ioteam.order_management_platform.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "주문", description = "주문 API")
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "주문 생성")
	@PostMapping()
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

	@Operation(summary = "전체 주문 조회")
	@GetMapping("/all")
	public ResponseEntity<CommonResponse<OrderListResponseDto>> getAllOrders() {
		OrderListResponseDto responseDto = orderService.getAllOrders();
		return ResponseEntity.ok(new CommonResponse<>("전체 주문 목록이 조회되었습니다.", responseDto));
	}


}
