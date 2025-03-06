package com.ioteam.order_management_platform.order.enums;

public enum OrderStatus {
	FAILED,
	CANCELED,
	WAITING,
	COMPLETED;

	public boolean isCompleted() {
		return OrderStatus.COMPLETED.equals(this);
	}
}
