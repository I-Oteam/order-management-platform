package com.ioteam.order_management_platform.payment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.payment.entity.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponseDto {

	private UUID paymentId;
	private String paymentStatus;
	private LocalDateTime paymentDate;

	public static PaymentResponseDto fromEntity(Payment payment) {
		return new PaymentResponseDto(
			payment.getPaymentId(),
			payment.getPaymentStatus(),
			payment.getCreatedAt()
		);
	}
}
