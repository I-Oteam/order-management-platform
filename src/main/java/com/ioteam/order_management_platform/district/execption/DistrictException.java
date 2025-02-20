package com.ioteam.order_management_platform.district.execption;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum DistrictException implements ExceptionType {
	DISTRICT_NOT_FOUND(HttpStatus.BAD_REQUEST, "지역을 찾을 수 없습니다.", "E_DISTRICT_NOT_FOUND");

	private final HttpStatus Status;
	private final String message;
	private final String errorCode;

	DistrictException(HttpStatus status, String message, String errorCode) {
		this.Status = status;
		this.message = message;
		this.errorCode = errorCode;
	}

	@Override
	public HttpStatus getStatus() {
		return this.Status;
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
