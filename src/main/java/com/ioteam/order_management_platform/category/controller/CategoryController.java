package com.ioteam.order_management_platform.category.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.category.dto.CategoryResponseDto;
import com.ioteam.order_management_platform.category.dto.CreateCategoryRequestDto;
import com.ioteam.order_management_platform.category.service.CategoryService;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "카테고리", description = "카테고리 API")
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping("/categories")
	@Operation(summary = "카테고리 등록", description = "카테고리 등록은 'MANAGER' 만 가능")
	public ResponseEntity<CommonResponse<CategoryResponseDto>> createCategory(
		@RequestBody @Valid CreateCategoryRequestDto categoryRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto, userDetails);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/categories")
			.build()
			.toUri();

		return ResponseEntity.created(location)
			.body(new CommonResponse<>("카테고리 생성 완료", categoryResponseDto));

	}

}
