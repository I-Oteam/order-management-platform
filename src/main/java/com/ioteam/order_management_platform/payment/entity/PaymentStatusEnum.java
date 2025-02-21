package com.ioteam.order_management_platform.payment.entity;

import lombok.Getter;

@Getter
public enum PaymentStatusEnum {
	PENDING,
	COMPLETED,
	FAILED,
	CANCELLED //5분 안에 주문이 취소되는데, 결제가 이미 들어가 있는 상태일때
}