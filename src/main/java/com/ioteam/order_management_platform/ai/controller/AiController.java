package com.ioteam.order_management_platform.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteam.order_management_platform.ai.dto.req.RecommendDesRequestDto;
import com.ioteam.order_management_platform.ai.dto.res.AnswerAiResponseDto;
import com.ioteam.order_management_platform.ai.service.AiService;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Tag(name = "AI", description = "AI API")
public class AiController {

	private final AiService aiService;

	@Operation(summary = "AI 상품 설명 추천 기능", description = "가게 주인이 작성한 5개의 키워드와 가게 정보와 메뉴 정보로 상품 설명 문구를 추천합니다.")
	@GetMapping("/menu-description")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
	public ResponseEntity<CommonResponse<AnswerAiResponseDto>> getAiMenuDescription(
		@RequestBody RecommendDesRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		AnswerAiResponseDto responseDto = aiService.recommendMenuDescription(userDetails, requestDto);
		return ResponseEntity.ok(new CommonResponse<>("code", responseDto));
	}
}
