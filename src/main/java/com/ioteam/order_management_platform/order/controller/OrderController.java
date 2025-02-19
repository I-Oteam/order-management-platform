package com.ioteam.order_management_platform.order.controller;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.order.dto.req.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.res.OrderListResponseDto;
import com.ioteam.order_management_platform.order.dto.res.OrderResponseDto;
import com.ioteam.order_management_platform.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "주문", description = "주문 API")
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "주문 생성")
	//@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping()
	public ResponseEntity<CommonResponse<OrderResponseDto>> createOrder(@Validated @RequestBody CreateOrderRequestDto requestDto) {

		OrderResponseDto responseDto = orderService.createOrder(requestDto);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/orders/" + responseDto.getOrderId().toString())
				.build()
				.toUri();

		return ResponseEntity.created(location)
				.body(new CommonResponse<>(SuccessCode.ORDER_CREATE, responseDto));
	}

	@Operation(summary = "전체 주문 조회")
	//@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/all")
	public ResponseEntity<CommonResponse<OrderListResponseDto>> getAllOrders() {
		OrderListResponseDto responseDto = orderService.getAllOrders();
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_ALL_INFO, responseDto));
	}

	@Operation(summary = "주문 상세 조회")
	//@PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
	@GetMapping("/{order_id}")
	public ResponseEntity<CommonResponse<OrderResponseDto>> getOrderDetail(@PathVariable("order_id") UUID orderId) {
		OrderResponseDto responseDto = orderService.getOrderDetail(orderId);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_DETAIL_INFO, responseDto));
	}

	@Operation(summary = "주문 취소")
	@PatchMapping("/{order_id}")
	public ResponseEntity<CommonResponse<OrderResponseDto>> modifyOrder(
			@PathVariable("order_id") UUID orderId) {
		OrderResponseDto responseDto = orderService.getOrderDetail(orderId);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_MODIFY, responseDto));
	}

}
