package com.ioteam.order_management_platform.payment.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.payment.entity.Payment;
import com.ioteam.order_management_platform.payment.entity.PaymentStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

	private final UUID paymentId;
	private final String resName;
	private final BigDecimal paymentTotal;
	private final String paymentMethod;
	private final PaymentStatusEnum paymentStatus;
	private final LocalDateTime createDate;
	private final LocalDateTime succeedDate;
	private final LocalDateTime failedDate;

	public static PaymentResponseDto from(Payment payment) {
		return PaymentResponseDto.builder()
			.paymentId(payment.getPaymentId())
			.resName(payment.getOrder().getRestaurant().getResName())
			.paymentTotal(payment.getPaymentTotal())
			.paymentMethod(payment.getPaymentMethod())
			.paymentStatus(payment.getPaymentStatus())
			.createDate(payment.getCreatedAt())
			.succeedDate(payment.getPaymentCompletedAt())
			.failedDate(payment.getPaymentFailedAt())
			.build();
	}
}
