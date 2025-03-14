package com.ioteam.order_management_platform.order.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.order.dto.req.AdminOrderSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.CancelOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.CreateOrderRequestDto;
import com.ioteam.order_management_platform.order.dto.req.OrderByRestaurantSearchCondition;
import com.ioteam.order_management_platform.order.dto.req.OrderByUserSearchCondition;
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

	@Operation(summary = "주문 생성", description = "주문 생성은 'CUSTOMER', 'OWNER' 만  가능")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'OWNER')")
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

	@Operation(summary = "전체 주문 조회", description = "전체 주문 조회는 'MANAGER', 'MASTER' 만  가능")
	@PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
	@GetMapping("/all")
	public ResponseEntity<CommonResponse<CommonPageResponse<OrderResponseDto>>> getAllOrders(
		AdminOrderSearchCondition condition,
		@PageableDefault
		@SortDefault.SortDefaults({
			@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
			@SortDefault(sort = "modifiedAt", direction = Sort.Direction.DESC)
		}) Pageable pageable
	) {
		CommonPageResponse<OrderResponseDto> responseDto = orderService.searchOrderByAdmin(condition, pageable);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_ALL_INFO, responseDto));
	}

	@Operation(summary = "주문 상세 조회", description = "주문 상세 조회는 'CUSTOMER', 'MANAGER' 만  가능")
	@PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
	@GetMapping("/{order_id}")
	public ResponseEntity<CommonResponse<OrderResponseDto>> getOrderDetail(@PathVariable("order_id") UUID orderId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		OrderResponseDto responseDto = orderService.getOrderDetail(orderId, userDetails);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_DETAIL_INFO, responseDto));
	}

	@Operation(summary = "주문 취소", description = "주문 취소는 'CUSTOMER', 'OWNER', 'MANAGER' 만  가능")
	@PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER', 'OWNER')")
	@PatchMapping("/{order_id}")
	public ResponseEntity<CommonResponse<OrderResponseDto>> cancelOrder(@PathVariable("order_id") UUID orderId,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody CancelOrderRequestDto requestDto) {
		OrderResponseDto responseDto = orderService.cancelOrder(orderId, requestDto, userDetails);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_CANCEL, responseDto));
	}

	@Operation(summary = "주문 삭제", description = "주문 삭제는 'CUSTOMER', 'MANAGER' 만  가능")
	@PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
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
		@SortDefault.SortDefaults({
			@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
			@SortDefault(sort = "modifiedAt", direction = Sort.Direction.DESC)
		}) Pageable pageable
	) {

		CommonPageResponse<OrderResponseDto> pageResponse = orderService.searchOrderByRestaurant(
			userDetails, resId, condition, pageable);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_SEARCH, pageResponse));
	}

	@Operation(summary = "사용자별 주문 조회", description = "사용자별 주문 조회는 'CUSTOMER', 'MANAGER', 'MASTER' 만  가능")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER', 'MASTER')")
	@GetMapping("/users/{userId}")
	public ResponseEntity<CommonResponse<CommonPageResponse<OrderResponseDto>>> searchOrdersByUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID userId,
		OrderByUserSearchCondition condition,
		@PageableDefault
		@SortDefault.SortDefaults({
			@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
			@SortDefault(sort = "modifiedAt", direction = Sort.Direction.DESC)}
		) Pageable pageable
	) {

		CommonPageResponse<OrderResponseDto> pageResponse = orderService.searchOrderByUser(
			userDetails, userId, condition, pageable);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.ORDER_SEARCH, pageResponse));
	}
}