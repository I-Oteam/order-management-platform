package com.ioteam.order_management_platform.ai.exception;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum AIException implements ExceptionType {

	INVALID_AI_RESPONSE(HttpStatus.BAD_REQUEST, "AI 응답이 올바르지 않습니다.", "E_INVALID_AI_RESPONSE"),
	AI_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "AI 서비스 연동 중 오류가 발생했습니다.", "E_AI_SERVICE_UNAVAILABLE");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	AIException(HttpStatus status, String message, String errorCode) {
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
