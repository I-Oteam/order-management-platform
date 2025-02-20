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
	USER_DELETE(HttpStatus.OK, "유저가 탈퇴하였습니다.", "S_USER_DELETE"),

	// review
	REVIEW_CREATE(HttpStatus.CREATED, "리뷰가 성공적으로 생성되었습니다.", "S_REVIEW_CREATE"),
	REVIEW_SEARCH(HttpStatus.OK, "리뷰가 성공적으로 조회되었습니다.", "S_REVIEW_SEARCH"),
	REVIEW_DELETE(HttpStatus.OK, "리뷰가 성공적으로 삭제되었습니다.", "S_REVIEW_DELETE"),
	REVIEW_MODIFY(HttpStatus.OK, "리뷰가 성공적으로 수정되었습니다.", "S_REVIEW_MODIFY"),

	// restaurant

	// category
	CATEGORY_CREATE(HttpStatus.CREATED, "카테고리가 성공적으로 생성되었습니다.", "S_CATEGORY_CREATE"),
	CATEGORY_ONE_SEARCH(HttpStatus.FOUND, "단건 카테고리 조회가 완료되었습니다.", "S_CATEGORY_ONE_SEARCH"),
	CATEGORY_SEARCH(HttpStatus.OK, "모든 카테고리 조회가 완료되었습니다.", "S_CATEGORY_SEARCH"),
	CATEGORY_DELETE(HttpStatus.ACCEPTED, "카테고리가 성공적으로 삭제되었습니다.", "S_CATEGORY_DELETE"),
	// 응답 본문이 필요없어서 ok대신 no_content
	CATEGORY_MODIFY(HttpStatus.NO_CONTENT, "카테고리가 성공적으로 수정되었습니다.", "S_CATEGORY_MODIFY"),

	// order
	ORDER_CREATE(HttpStatus.OK, "주문이 성공적으로 생성되었습니다.", "S_ORDER_CREATE"),
	ORDER_ALL_INFO(HttpStatus.OK, "전체 주문을 조회하는데에 성공하였습니다.", "S_ORDER_LIST_INFO"),
	ORDER_DETAIL_INFO(HttpStatus.OK, "주문 상세정보를 조회하는데에 성공하였습니다.", "S_ORDER_LIST_INFO"),
	ORDER_CANCEL(HttpStatus.OK, "주문이 성공적으로 취소되었습니다.", "S_ORDER_CANCEL"),
	ORDER_DELETE(HttpStatus.OK, "주문이 성공적으로 삭제되었습니다.", "S_ORDER_DELETE"),

	// menu
	MENU_LIST_INFO(HttpStatus.OK, "메뉴 목록을 조회하는데에 성공하였습니다.", "S_MENU_LIST_INFO"),
	MENU_DETAIL_INFO(HttpStatus.OK, "메뉴 상세정보를 조회하는데에 성공하였습니다.", "S_MENU_LIST_INFO"),
	MENU_CREATE(HttpStatus.CREATED, "메뉴를 성공적으로 등록하였습니다.", "S_MENU_LIST_INFO"),
	MENU_MODIFY(HttpStatus.OK, "메뉴를 성공적으로 수정하였습니다.", "S_MENU_MODIFY"),
	MENU_DELETE(HttpStatus.NO_CONTENT, "메뉴를 성공적으로 삭제하였습니다.", "S_MENU_DELETE"),

	// payment
	PAYMENT_CREATE(HttpStatus.CREATED, "결제가 성공적으로 요청되었습니다.", "S_PAYMENT_CREATE"),
	PAYMENT_SEARCH(HttpStatus.OK, "결제가 성공적으로 조회되었습니다.", "S_PAYMENT_SEARCH"),
	// ai
	;
	private final HttpStatus statusCode;
	private final String message;
	private final String code;
}
