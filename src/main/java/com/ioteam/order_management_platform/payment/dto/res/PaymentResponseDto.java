package com.ioteam.order_management_platform.payment.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.payment.entity.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

	private UUID paymentId;
	private BigDecimal paymentTotal;
	private String paymentStatus;
	private LocalDateTime createDate;
	private LocalDateTime succeedDate;
	private LocalDateTime failedDate;

	public static PaymentResponseDto fromEntity(Payment payment) {
		return PaymentResponseDto.builder()
			.paymentId(payment.getPaymentId())
			.paymentTotal(payment.getPaymentTotal())
			.paymentStatus(payment.getPaymentStatus())
			.createDate(payment.getCreatedAt())
			.succeedDate(payment.getPaymentCompletedAt())
			.failedDate(payment.getPaymentFailedAt())
			.build();
	}
}
