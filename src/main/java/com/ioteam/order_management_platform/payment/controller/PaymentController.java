package com.ioteam.order_management_platform.payment.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.payment.dto.CreatePaymentRequestDto;
import com.ioteam.order_management_platform.payment.dto.PaymentResponseDto;
import com.ioteam.order_management_platform.payment.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Tag(name = "결제", description = "결제 API")
public class PaymentController {
	private final PaymentService paymentService;

	@Operation(summary = "결제 요청")
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
}
