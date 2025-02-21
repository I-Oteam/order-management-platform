package com.ioteam.order_management_platform.category.execption;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum CategoryException implements ExceptionType {

	INVALID_CATEGORY_INFO(HttpStatus.BAD_REQUEST, "잘못된 카테고리 정보입니다.", "E_VALIDATION"),
	EMPTY_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "카테고리명은 공백일 수 없습니다.", "E_EMPTY_CATEGORY_NAME"),
	DUPLICATE_CATEGORY_NAME(HttpStatus.CONFLICT, "중복된 카테고리 이름입니다.", "E_DUPLICATE_CATEGORY_NAME"),
	NOT_AUTHORIZED_ROLE(HttpStatus.FORBIDDEN, "허가받지 않은 역할입니다.", "E_NOT_AUTHORIZED_ROLE"),
	CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다.", "E_CATEGORY_NOT_FOUND");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	CategoryException(HttpStatus status, String message, String errorCode) {
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
