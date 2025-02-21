package com.ioteam.order_management_platform.order.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.order.dto.req.CancelOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.OrderByRestaurantSearchCondition;
import com.ioteam.order_management_platform.order.dto.res.OrderListResponseDto;
import com.ioteam.order_management_platform.order.dto.res.OrderResponseDto;
import com.ioteam.order_management_platform.order.service.OrderService;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "주문", description = "주문 API")
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "주문 생성")
	//@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping()
	public ResponseEntity<CommonResponse<OrderResponseDto>> createOrder(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@Validated @RequestBody CreateOrderRequestDto requestDto) {

		OrderResponseDto responseDto = orderService.createOrder(userDetails.getUserId(), userDetails.getRole(),
			requestDto);

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
	public ResponseEntity<CommonResponse<OrderResponseDto>> cancelOrder(@PathVariable("order_id") UUID orderId,
		@RequestBody CancelOrderRequestDto requestDto) {
		OrderResponseDto responseDto = orderService.cancelOrder(orderId, requestDto);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_CANCEL, responseDto));
	}

	@Operation(summary = "주문 삭제")
	@PreAuthorize("hasAnyRole('MANAGER', 'OWNER')")
	@DeleteMapping("/{orderId}")
	public ResponseEntity<CommonResponse<Void>> softDeleteOrder(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID orderId
	) {
		orderService.softDeleteOrder(orderId, userDetails);

		return ResponseEntity
			.ok()
			.body(new CommonResponse<>(SuccessCode.ORDER_DELETE, null));
	}

	@Operation(summary = "가게별 주문 조회", description = "가게별 주문 조회는 'OWNER', 'MANAGER', 'MASTER' 만  가능")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	@GetMapping("/restaurants/{resId}")
	public ResponseEntity<CommonResponse<CommonPageResponse<OrderResponseDto>>> searchOrdersByRestaurant(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID resId,
		OrderByRestaurantSearchCondition condition,
		@PageableDefault
		@SortDefault.SortDefaults(
			{@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
				@SortDefault(sort = "modifiedAt", direction = Sort.Direction.DESC)}
		) Pageable pageable
	) {

		CommonPageResponse<OrderResponseDto> pageResponse = orderService.searchOrderByRestaurant(
			userDetails, resId, condition, pageable);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_SEARCH, pageResponse));
	}
}
