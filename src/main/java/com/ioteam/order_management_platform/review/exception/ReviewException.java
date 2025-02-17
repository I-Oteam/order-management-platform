package com.ioteam.order_management_platform.review.exception;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public enum ReviewException implements ExceptionType {

    INVALID_REVIEW_ID(HttpStatus.NOT_FOUND, "review 아이디가 유효하지 않습니다.", "E_INVALID_REVIEW_ID"),
    INVALID_PERIOD(HttpStatus.UNPROCESSABLE_ENTITY, "기간 검색 조건이 부적합합니다.", "E_INVALID_P");

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
