package com.ioteam.order_management_platform.restaurant.execption;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum RestaurantException implements ExceptionType {
	NOT_AUTHORIZED_ROLE(HttpStatus.FORBIDDEN, "허가받지 않은 역할입니다.", "E_NOT_AUTHORIZED_ROLE"),
	;

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	RestaurantException(HttpStatus status, String message, String errorCode) {
		this.status = status;
		this.message = message;
		this.errorCode = errorCode;
	}

	@Override
	public HttpStatus getStatus() {
		return this.status;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getErrorCode() {
		return this.errorCode;
	}
}
