package com.ioteam.order_management_platform.restaurant.execption;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum RestaurantException implements ExceptionType {
	NOT_AUTHORIZED_ROLE(HttpStatus.FORBIDDEN, "허가받지 않은 역할입니다.", "E_NOT_AUTHORIZED_ROLE"),
	NOT_FOUND_RESTAURANT(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다.", "E_NOT_FOUND_RESTAURANT"),
	MISMATCH_OWNER(HttpStatus.FORBIDDEN, "해당 가게의 주인이 아닙니다.", "E_MISMATCH_OWNER"),
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
