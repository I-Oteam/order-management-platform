package com.ioteam.order_management_platform.user.exception;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;

public enum UserException implements ExceptionType {
    INVALID_USER_INFO(HttpStatus.BAD_REQUEST, "잘못된 사용자 정보입니다.", "E_VALIDATION"),
    EMPTY_USERNAME(HttpStatus.BAD_REQUEST, "username은 공백일 수 없습니다.", "E_EMPTY_USERNAME"),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "password는 공백일 수 없습니다.", "E_EMPTY_PASSWORD"),
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST, "email은 공백일 수 없습니다.", "E_EMPTY_EMAIL"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "email 형식이 올바르지 않습니다.", "E_INVALID_EMAIL"),
    DUPLICATE_USER(HttpStatus.CONFLICT, "중복된 사용자가 존재합니다.", "E_DUPLICATE_USER"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 Email 입니다.", "E_DUPLICATE_EMAIL"),
    INVALID_ADMIN_TOKEN(HttpStatus.UNAUTHORIZED, "관리자 암호가 틀려 등록이 불가능합니다.", "E_INVALID_ADMIN_TOKEN"),
    INVALID_OWNER_TOKEN(HttpStatus.UNAUTHORIZED, "점주 인증 암호가 틀려 등록이 불가능합니다.", "E_INVALID_OWNER_TOKEN");


    private final HttpStatus status;
    private final String message;
    private final String errorCode;

    UserException(HttpStatus status, String message, String errorCode) {
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