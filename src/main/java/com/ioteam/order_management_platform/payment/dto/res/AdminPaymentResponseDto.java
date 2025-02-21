package com.ioteam.order_management_platform.payment.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.payment.entity.Payment;
import com.ioteam.order_management_platform.payment.entity.PaymentStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AdminPaymentResponseDto {

	private final UUID paymentId;
	private final UUID customerId;
	private final UUID restaurantId;
	private final BigDecimal paymentTotal;
	private final String paymentMethod;
	private final String paymentNumber;
	private final PaymentStatusEnum paymentStatus;
	private final LocalDateTime createdAt;

	public static AdminPaymentResponseDto from(Payment payment) {
		return AdminPaymentResponseDto
			.builder()
			.paymentId(payment.getPaymentId())
			.customerId(payment.getOrder().getUser().getUserId())
			.restaurantId(payment.getOrder().getRestaurant().getResId())
			.paymentTotal(payment.getPaymentTotal())
			.paymentMethod(payment.getPaymentMethod())
			.paymentNumber(payment.getPaymentNumber())
			.paymentStatus(payment.getPaymentStatus())
			.build();
	}
}
