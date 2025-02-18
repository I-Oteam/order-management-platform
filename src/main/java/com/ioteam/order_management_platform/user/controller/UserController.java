package com.ioteam.order_management_platform.user.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.user.dto.AdminUserResponseDto;
import com.ioteam.order_management_platform.user.dto.LoginRequestDto;
import com.ioteam.order_management_platform.user.dto.SignupRequestDto;
import com.ioteam.order_management_platform.user.dto.UserInfoResponseDto;
import com.ioteam.order_management_platform.user.dto.UserSearchCondition;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;
import com.ioteam.order_management_platform.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "사용자", description = "사용자 API")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	@Operation(summary = "회원가입", description = "회원가입은 인증/비인증 회원 모두 사용 가능")
	public ResponseEntity<CommonResponse<Void>> signup(@RequestBody @Validated SignupRequestDto requestDto) {
		userService.signup(requestDto);
		return ResponseEntity.ok(new CommonResponse<>("회원가입이 성공적으로 완료되었습니다.", null));
	}

	@PostMapping("/login")
	@Operation(summary = "로그인", description = "로그인은 인증/비인증 회원 모두 사용 가능")
	public ResponseEntity<CommonResponse<String>> login(@RequestBody @Validated LoginRequestDto requestDto) {
		String token = userService.login(requestDto);
		return ResponseEntity.ok(new CommonResponse<>("로그인이 되었습니다.", token));
	}

	@GetMapping("/{userId}")
	@Operation(summary = "사용자 상세 조회", description = "사용자 상세 조회는 인증된 회원만 사용 가능")
	public ResponseEntity<CommonResponse<UserInfoResponseDto>> getUserByUserId(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable UUID userId) {
		UserInfoResponseDto userInfo = userService.getUserByUserId(userDetails, userId);
		return ResponseEntity.ok(new CommonResponse<>("사용자 조회 성공", userInfo));
	}

	@GetMapping("/all")
	@Operation(summary = "사용자 전체 조회", description = "사용자 전체 조회는 MANAGER와 MASTER만 사용 가능")
	public ResponseEntity<CommonResponse<CommonPageResponse<AdminUserResponseDto>>> searchUsers(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		UserSearchCondition condition,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		CommonPageResponse<AdminUserResponseDto> pageResponse = userService.searchUsersByCondition(userDetails,
			condition, pageable);
		return ResponseEntity.ok(new CommonResponse<>(
			"전체 사용자 조회 성공",
			pageResponse));
	}
}