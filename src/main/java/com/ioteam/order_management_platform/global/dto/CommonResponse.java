package com.ioteam.order_management_platform.global.dto;

import com.ioteam.order_management_platform.global.success.SuccessCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> {
	private String message;
	private T result;

	public CommonResponse(String message, T result) {
		this.message = message;
		this.result = result;
	}

	public CommonResponse(SuccessCode successCode, T result) {
		this.message = successCode.getMessage();
		this.result = result;
	}
}