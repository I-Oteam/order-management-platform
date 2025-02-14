package com.ioteam.order_management_platform.global.exception.type;

import org.springframework.http.HttpStatus;

public interface ExceptionType {
    HttpStatus getStatus();
    String getMessage();
    String getErrorCode();
}
