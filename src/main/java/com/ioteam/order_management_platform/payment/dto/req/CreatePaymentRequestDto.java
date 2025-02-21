package com.ioteam.order_management_platform.payment.dto.req;

import java.math.BigDecimal;
import java.util.UUID;

import com.ioteam.order_management_platform.order.entity.Order;
import com.ioteam.order_management_platform.payment.entity.Payment;
import com.ioteam.order_management_platform.payment.entity.PaymentStatusEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CreatePaymentRequestDto {
	@NotNull
	private UUID orderId;
	@NotNull
	private String paymentMethod;
	@NotNull
	private BigDecimal paymentTotal;

	public Payment toEntity(Order order) {
		return Payment
			.builder()
			.paymentTotal(paymentTotal)
			.paymentMethod(paymentMethod)
			.paymentStatus(PaymentStatusEnum.valueOf("PENDING"))
			.order(order)
			.build();
	}
}
