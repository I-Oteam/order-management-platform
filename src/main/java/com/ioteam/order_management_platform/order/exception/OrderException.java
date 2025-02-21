package com.ioteam.order_management_platform.order.exception;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum OrderException implements ExceptionType {

	INVALID_ORDER(HttpStatus.BAD_REQUEST, "주문이 존재하지 않습니다.", "E_INVALID_ORDER"),
	INVALID_ORDER_ID(HttpStatus.BAD_REQUEST, "주문 아이디가 유효하지 않습니다.", "E_INVALID_ORDER_ID"),
	INVALID_ORDER_RES_ID(HttpStatus.BAD_REQUEST, "가게 아이디가 유효하지 않습니다.", "E_INVALID_ORDER_RES_ID"),
	INVALID_ORDER_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 주문 타입입니다.", "E_INVALID_ORDER_TYPE"),
	INVALID_ORDER_RES_TOTAL(HttpStatus.BAD_REQUEST, "총 주문 금액은 0원보다 커야 합니다.", "E_INVALID_ORDER_RES_TOTAL"),
	UNAUTH_OWNER(HttpStatus.FORBIDDEN, "해당 가게의 오너가 아닙니다.", "E_UNAUTH_OWNER");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	OrderException(HttpStatus status, String message, String errorCode) {
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

