package com.ioteam.order_management_platform.global.success;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

	// user
	USER_SIGNUP(HttpStatus.CREATED, "회원가입이 성공적으로 완료되었습니다.", "S_USER_SIGNUP"),
	USER_LOGIN(HttpStatus.OK, "로그인에 성공하였습니다.", "S_USER_LOGIN"),
	USER_DETAILS_INFO(HttpStatus.OK, "회원 정보를 가져오는데에 성공하였습니다.", "S_USER_DETAILS_INFO"),
	USER_INFO(HttpStatus.OK, "모든 회원 정보를 가져오는데에 성공하였습니다.", "S_USER_INFO"),

	// review
	REVIEW_CREATE(HttpStatus.CREATED, "리뷰가 성공적으로 생성되었습니다.", "S_REVIEW_CREATE"),
	REVIEW_SEARCH(HttpStatus.OK, "리뷰가 성공적으로 조회되었습니다.", "S_REVIEW_SEARCH"),
	REVIEW_DELETE(HttpStatus.OK, "리뷰가 성공적으로 삭제되었습니다.", "S_REVIEW_DELETE"),
	REVIEW_MODIFY(HttpStatus.OK, "리뷰가 성공적으로 수정되었습니다.", "S_REVIEW_MODIFY");
	
	// restaurant

	// category

	// order

	// menu

	// payment

	// ai

	private final HttpStatus statusCode;
	private final String message;
	private final String code;
}
