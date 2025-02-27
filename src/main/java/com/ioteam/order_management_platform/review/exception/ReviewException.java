package com.ioteam.order_management_platform.review.exception;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum ReviewException implements ExceptionType {

	INVALID_REVIEW_ID(HttpStatus.NOT_FOUND, "review 아이디가 유효하지 않습니다.", "E_INVALID_REVIEW_ID"),
	INVALID_PERIOD(HttpStatus.UNPROCESSABLE_ENTITY, "기간 검색 조건이 부적합합니다.", "E_INVALID_PERIOD"),
	INVALID_ORDER_ID(HttpStatus.NOT_FOUND, "order 아이디가 유효하지 않습니다.", "E_INVALID_ORDER_ID"),
	UNAUTH_ORDER_ID(HttpStatus.FORBIDDEN, "order 아이디에 권한이 없습니다.", "E_UNAUTH_ORDER_ID"),
	INVALID_IDS(HttpStatus.BAD_REQUEST, "order, user, restaurant 아이디가 적합하지 않습니다.", "E_INVALID_IDS"),
	UNCOMPLETED_ORDER_ID(HttpStatus.UNPROCESSABLE_ENTITY, "완료되지 않은 주문에 리뷰를 쓸 수 없습니다.", "E_UNCOMPLETED_ORDER");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	ReviewException(HttpStatus status, String message, String errorCode) {
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
