package com.ioteam.order_management_platform.payment.exception;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum PaymentException implements ExceptionType {

	INVALID_USERNAME(HttpStatus.NOT_FOUND, "주문번호가 존재하지 않습니다.", "E_INVALID_USERNAME"),
	PAYMENT_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 결제가 완료된 주문입니다.", "E_PAYMENT_ALREADY_COMPLETED"),
	;

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	PaymentException(HttpStatus status, String message, String errorCode) {
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
