package com.ioteam.order_management_platform.global.dto;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;
import lombok.Getter;

@Getter
public class CommonErrorResponse {
    private String message;
    private String errorCode;

    public CommonErrorResponse(String message, ExceptionType exceptionType) {
        this.message = message;
        this.errorCode = exceptionType.getErrorCode();
    }
}