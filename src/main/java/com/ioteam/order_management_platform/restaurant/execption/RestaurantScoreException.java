package com.ioteam.order_management_platform.restaurant.execption;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum RestaurantScoreException implements ExceptionType {

	NOT_FOUND_SCORE(HttpStatus.NOT_FOUND, "스코어보드를 찾을 수 없습니다.", "E_NOT_FOUND");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	RestaurantScoreException(HttpStatus status, String message, String errorCode) {
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
