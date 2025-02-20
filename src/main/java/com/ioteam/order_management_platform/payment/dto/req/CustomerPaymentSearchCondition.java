package com.ioteam.order_management_platform.payment.dto.req;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerPaymentSearchCondition {
	private String restaurantName;
	private LocalDateTime startCreatedAt;
	private LocalDateTime endCreatedAt;
	private BigDecimal minPaymentTotal;
	private BigDecimal maxPaymentTotal;
	private String paymentMethod;
	private String paymentStatus;
}