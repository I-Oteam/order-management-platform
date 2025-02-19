package com.ioteam.order_management_platform.user.exception;

import org.springframework.http.HttpStatus;

import com.ioteam.order_management_platform.global.exception.type.ExceptionType;

public enum UserException implements ExceptionType {
	INVALID_USER_INFO(HttpStatus.BAD_REQUEST, "잘못된 사용자 정보입니다.", "E_VALIDATION"),
	DUPLICATE_FIELD(HttpStatus.CONFLICT, "중복된 필드가 존재합니다.", "E_DUPLICATE_FIELD"),
	INVALID_ROLE(HttpStatus.BAD_REQUEST, "잘못된 권한입니다.", "E_INVALID"),
	INVALID_MASTER_TOKEN(HttpStatus.UNAUTHORIZED, "MASTER 암호가 틀려 등록이 불가능합니다.", "E_INVALID_MASTER_TOKEN"),
	INVALID_MANAGER_TOKEN(HttpStatus.UNAUTHORIZED, "관리자 암호가 틀려 등록이 불가능합니다.", "E_INVALID_MANAGER_TOKEN"),
	INVALID_OWNER_TOKEN(HttpStatus.UNAUTHORIZED, "점주 인증 암호가 틀려 등록이 불가능합니다.", "E_INVALID_OWNER_TOKEN"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 ID가 존재하지 않습니다.", "E_USER_NOT_FOUND"),
	USER_DELETED(HttpStatus.NOT_FOUND, "삭제된 사용자입니다.", "E_USER_DELETED"),
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인한 유저와 일치하지 않습니다.", "E_UNAUTHORIZED_ACCESS"),
	INVALID_USERNAME(HttpStatus.UNAUTHORIZED, "사용자 정보가 없습니다.", "E_INVALID_USERNAME"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "암호가 틀렸습니다.", "E_INVALID_PASSWORD"),
	INVALID_PERIOD(HttpStatus.UNPROCESSABLE_ENTITY, "기간 검색 조건이 적합하지 않습니다.", "E_INVALID_P"),
	NO_PERMISSION(HttpStatus.UNAUTHORIZED, "관리자 권한이 필요합니다.", "E_NO_PERMISSION"),
	INVALID_USER_ID(HttpStatus.NOT_FOUND, "user 아이디가 유효하지 않습니다.", "E_INVALID_USER_ID");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	UserException(HttpStatus status, String message, String errorCode) {
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