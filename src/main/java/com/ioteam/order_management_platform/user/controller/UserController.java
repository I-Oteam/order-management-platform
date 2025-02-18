package com.ioteam.order_management_platform.user.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.user.dto.LoginRequestDto;
import com.ioteam.order_management_platform.user.dto.SignupRequestDto;
import com.ioteam.order_management_platform.user.dto.UserInfoResponseDto;
import com.ioteam.order_management_platform.user.exception.UserException;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;
import com.ioteam.order_management_platform.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<CommonResponse<Void>> signup(@Valid @RequestBody SignupRequestDto requestDto,
		BindingResult bindingResult) {
		// Validation 예외처리
		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				log.error("Validation error - field: {}, message: {}", error.getField(), error.getDefaultMessage());

				switch (error.getField()) {
					case "nickname":
						throw new CustomApiException(UserException.EMPTY_NICKNAME);
					case "username":
						throw new CustomApiException(UserException.EMPTY_USERNAME);
					case "password":
						throw new CustomApiException(UserException.EMPTY_PASSWORD);
					case "email":
						if ("Email".equals(error.getCode())) {
							throw new CustomApiException(UserException.INVALID_EMAIL_FORMAT);
						}
						throw new CustomApiException(UserException.EMPTY_EMAIL);
					default:
						throw new CustomApiException(UserException.INVALID_USER_INFO);
				}
			}
		}
		userService.signup(requestDto);
		return ResponseEntity.ok(new CommonResponse<>("회원가입이 성공적으로 완료되었습니다.", null));
	}

	@PostMapping("/login")
	public ResponseEntity<CommonResponse<String>> login(@Valid @RequestBody LoginRequestDto requestDto) {
		String token = userService.login(requestDto);
		return ResponseEntity.ok(new CommonResponse<>("로그인이 되었습니다.", token));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<CommonResponse<UserInfoResponseDto>> getUserByUserId(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID userId) {
		UserInfoResponseDto userInfo = userService.getUserByUserId(userDetails, userId);
		return ResponseEntity.ok(new CommonResponse<>("사용자 조회 성공", userInfo));
	}
}