package com.ioteam.order_management_platform.menu.exception;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum MenuException implements ExceptionType {

	INVALID_RESTAURANT_ID(HttpStatus.NOT_FOUND, "restaurant 아이디가 유효하지 않습니다.", "E_INVALID_REVIEW_ID"),
	INVALID_MENU(HttpStatus.NOT_FOUND, "조회하려는 상품이 존재하지 않습니다.", "E_INVALID_MENU_ID"),
	NOT_AUTHORIZED_FOR_MENU(HttpStatus.FORBIDDEN, "해당 상품에 대한 권한이 없습니다.", "E_NOT_AUTHORIZED_FOR_MENU");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	MenuException(HttpStatus status, String message, String errorCode) {
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
