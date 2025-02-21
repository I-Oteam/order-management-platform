package com.ioteam.order_management_platform.global.exception;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ioteam.order_management_platform.global.dto.CommonErrorResponse;
import com.ioteam.order_management_platform.global.exception.type.BaseException;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// 공통 커스텀 예외를 기준으로 오류를 제어한다.
	@ExceptionHandler(value = {CustomApiException.class})
	protected ResponseEntity<Object> handleCustomApiException(CustomApiException e, WebRequest request) {

		HttpHeaders headers = new HttpHeaders();
		return handleExceptionInternal(e, new CommonErrorResponse(e.getMessage(), e.getExceptionType()),
			headers, e.getStatus(), request);
	}

	@ExceptionHandler(value = {ValidationException.class})
	protected ResponseEntity<Object> handleValidationException(ValidationException e, WebRequest request) {

		HttpHeaders headers = new HttpHeaders();
		return handleExceptionInternal(e, new CommonErrorResponse(e.getMessage(), BaseException.INVALID_INPUT),
			headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = {AuthorizationDeniedException.class})
	protected ResponseEntity<Object> handleAuthorizationDeniedException(AuthorizationDeniedException e,
		WebRequest request) {

		HttpHeaders headers = new HttpHeaders();
		return handleExceptionInternal(e, new CommonErrorResponse(e.getMessage(), BaseException.UNAUTHORIZED_REQ),
			headers, HttpStatus.FORBIDDEN, request);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Object> internalServerError(Exception ex, WebRequest request) {

		HttpHeaders headers = new HttpHeaders();
		return handleExceptionInternal(ex, new CommonErrorResponse(ex.getMessage(), BaseException.SERVER_ERROR),
			headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e,
		WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		return handleExceptionInternal(e,
			new CommonErrorResponse(BaseException.DUPLICATE_FIELD.getMessage(), BaseException.DUPLICATE_FIELD),
			headers,
			HttpStatus.CONFLICT,
			request);
	}

	// 나머지 예외 처리는 오버라이드해서 커스텀할 수 있다.
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
		HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// 예외 메세지 확인을 위한 오버라이딩
		return handleExceptionInternal(ex, new CommonErrorResponse(ex.getMessage(), BaseException.SERVER_ERROR),
			headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		String message = "";
		for (FieldError fieldError : fieldErrors) {
			if (!message.equals(""))
				message += " | ";
			message = fieldError.getField() + " : " + fieldError.getDefaultMessage();
		}
		return handleExceptionInternal(ex, new CommonErrorResponse(message, BaseException.INVALID_INPUT), headers,
			status, request);
	}
}
