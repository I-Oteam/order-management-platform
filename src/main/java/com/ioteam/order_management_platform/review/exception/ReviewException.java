package com.ioteam.order_management_platform.review.exception;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public enum ReviewException implements ExceptionType {

    INVALID_REVIEW_ID(HttpStatus.NOT_FOUND, "Invalid Review Id", "E_INVALID_REVIEW_ID");

    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    ReviewException(HttpStatus status, String message, String errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public HttpStatus getStatus() { return this.status; }
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getErrorCode() { return this.errorCode; }
}
