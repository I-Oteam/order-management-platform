package com.ioteam.order_management_platform.payment.exception;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum PaymentException implements ExceptionType {

	INVALID_USERNAME(HttpStatus.NOT_FOUND, "주문번호가 존재하지 않습니다.", "E_INVALID_USERNAME"),
	PAYMENT_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 결제가 완료된 주문입니다.", "E_PAYMENT_ALREADY_COMPLETED"),
	INVALID_PAYMENT_ID(HttpStatus.NOT_FOUND, "payment ID가 유효하지 않습니다.", "E_INVALID_PAYMENT_ID"),
	UNAUTHORIZED_PAYMENT_ACCESS(HttpStatus.UNAUTHORIZED, "접근이 유효하지 않습니다.", "E_UNAUTHORIZED_PAYMENT_ACCESS"),
	INVALID_PERIOD(HttpStatus.UNPROCESSABLE_ENTITY, "기간 설정이 유효하지 않습니다.", "E_INVALID_PERIOD"),
	UNAUTHORIZED_REQ(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다.", "E_UNAUTHORIZED_REQ"),
	INVALID_USER(HttpStatus.FORBIDDEN, "현재 로그인한 사용자가 해당 결제를 진행할 권한이 없습니다.", "E_INVALID_USER"),
	RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "가게 정보가 조회되지 않습니다.", "E_RESTAURANT_NOT_FOUND"),
	PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보가 조회되지 않습니다.", "E_PAYMENT_NOT_FOUND"),
	INVALID_ORDER_OR_USER(HttpStatus.FORBIDDEN, "유효하지 않은 주문이거나 결제자가 올바르지 않습니다.", "INVALID_ORDER_OR_USER");


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
