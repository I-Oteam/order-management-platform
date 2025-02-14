package com.ioteam.order_management_platform.global.exception;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public class NoRollbackCustomApiException extends CustomApiException {

    public NoRollbackCustomApiException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
