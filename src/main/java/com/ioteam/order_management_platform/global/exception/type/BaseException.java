package com.ioteam.order_management_platform.global.exception.type;

import org.springframework.http.HttpStatus;

public enum BaseException implements ExceptionType {

	SERVER_ERROR(HttpStatus.BAD_REQUEST, "서버 오류가 발생하였습니다.", "E_NOT_DEFINED"),
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "유효성 검증에 실패했습니다.", "E_VALIDATION"),
	NULL_DATA(HttpStatus.BAD_REQUEST, "널 데이터가 존재해서는 안 됩니다.", "E_NULL"),
	UNAUTHORIZED_REQ(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다.", "E_UNAUTH"),
	DUPLICATE_FIELD(HttpStatus.CONFLICT, "중복된 데이터가 존재합니다.", "E_DUPLICATE"),
	INVALID_PAGESIZE(HttpStatus.BAD_REQUEST, "페이지 사이즈는 10, 30, 50만 가능합니다.", "E_PAGE_SIZE");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	BaseException(HttpStatus status, String message, String errorCode) {
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
