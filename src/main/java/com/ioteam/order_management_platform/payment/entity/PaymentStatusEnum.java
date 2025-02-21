package com.ioteam.order_management_platform.payment.entity;

import lombok.Getter;

@Getter
public enum PaymentStatusEnum {
	PENDING,
	COMPLETED,
	FAILED, // 결제 실패 시 재주문을 통해 결제해야 함. 한번 FAILED 되면 바뀌지 않게끔 설정
	CANCELLED //5분 안에 주문이 취소되는데, 결제가 이미 들어가 있는 상태일때
}