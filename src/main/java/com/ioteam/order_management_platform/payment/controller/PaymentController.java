package com.ioteam.order_management_platform.payment.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.payment.dto.req.CreatePaymentRequestDto;
import com.ioteam.order_management_platform.payment.dto.res.PaymentResponseDto;
import com.ioteam.order_management_platform.payment.service.PaymentService;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Tag(name = "결제", description = "결제 API")
public class PaymentController {
	private final PaymentService paymentService;

	@Operation(summary = "결제 요청", description = "결제 요청은 'CUSTOMER', 'MANAGER', 'MASTER' 만 가능")
	@PostMapping()
	@PreAuthorize("hasAnyRole('CUSTOMER','MANAGER','MASTER')")
	public ResponseEntity<CommonResponse<PaymentResponseDto>> createMenu(
		@RequestBody CreatePaymentRequestDto requestDto) {
		PaymentResponseDto responseDto = paymentService.createPayment(requestDto);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/payments/" + responseDto.getPaymentId().toString())
			.build()
			.toUri();
		return ResponseEntity.created(location)
			.body(new CommonResponse<>(SuccessCode.PAYMENT_CREATE, responseDto));
	}

	@Operation(summary = "결제 조회", description = "결제 조회는 인증된 사용자만 가능")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{paymentId}")
	public ResponseEntity<CommonResponse<PaymentResponseDto>> getPayment(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID paymentId) {

		PaymentResponseDto responseDto = paymentService.getPayment(paymentId, userDetails);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.PAYMENT_SEARCH, responseDto));
	}

}
