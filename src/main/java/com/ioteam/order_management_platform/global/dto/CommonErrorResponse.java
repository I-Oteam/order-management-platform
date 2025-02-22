package com.ioteam.order_management_platform.global.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CommonErrorResponse {
	private String message;
	private String errorCode;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ErrorField> errorFields;

	public CommonErrorResponse(ExceptionType exceptionType) {
		this.message = exceptionType.getMessage();
		this.errorCode = exceptionType.getErrorCode();
	}

	public CommonErrorResponse(String message, ExceptionType exceptionType) {
		this.message = message;
		this.errorCode = exceptionType.getErrorCode();
	}

	public CommonErrorResponse(ExceptionType exceptionType, List<ErrorField> errorFields) {
		this.message = exceptionType.getMessage();
		this.errorCode = exceptionType.getErrorCode();
		this.errorFields = errorFields;
	}

	@AllArgsConstructor
	@Getter
	public static class ErrorField {
		private String field;
		private String message;
	}
}