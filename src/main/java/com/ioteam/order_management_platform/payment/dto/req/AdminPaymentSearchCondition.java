package com.ioteam.order_management_platform.payment.dto.req;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdminPaymentSearchCondition {
	private UUID paymentId;
	private UUID restaurantId;
	private UUID customerId; //결제한 고객의 Id
	private UUID orderId;
	private LocalDateTime startCreatedAt;
	private LocalDateTime endCreatedAt;
	private BigDecimal minPaymentTotal;
	private BigDecimal maxPaymentTotal;
	private String paymentMethod;
	private String paymentNumber;
	private String paymentStatus;
	private Boolean isDeleted;
}
