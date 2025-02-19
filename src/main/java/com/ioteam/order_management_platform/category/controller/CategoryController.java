package com.ioteam.order_management_platform.category.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.category.dto.req.CreateCategoryRequestDto;
import com.ioteam.order_management_platform.category.dto.res.CategoryResponseDto;
import com.ioteam.order_management_platform.category.service.CategoryService;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
		@RequestBody @Validated CreateCategoryRequestDto categoryRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto, userDetails);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/categories")
			.build()
			.toUri();

		// 생성은 .created()사용
		return ResponseEntity.created(location)
			.body(new CommonResponse<>(SuccessCode.CATEGORY_CREATE, categoryResponseDto));

	}

	@GetMapping("/categories/{rcId}")
	@Operation(summary = "카테고리 단건 조회", description = "카테고리 조회는 'MANAGER' , 'OWNER' 만 가능")
	public ResponseEntity<CommonResponse<CategoryResponseDto>> getOneCategory(
		@PathVariable UUID rcId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		CategoryResponseDto categoryResponseDto = categoryService.readOneCategory(rcId, userDetails);

		// 생성을 제외한 조회, 삭제 , 수정은 .ok()사용
		return ResponseEntity.ok()
			.body(new CommonResponse<>(SuccessCode.CATEGORY_ONE_SEARCH, categoryResponseDto));
	}

	@GetMapping("/categories/all")
	@Operation(summary = "모든 카테고리 조회", description = "카테고리 조회는 'MANAGER' , 'OWNER' 만 가능")
	public ResponseEntity<CommonResponse<Page<CategoryResponseDto>>> getAllCategories(
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		Page<CategoryResponseDto> categories = categoryService.readAllCategories(pageable, userDetails);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(SuccessCode.CATEGORY_SEARCH, categories));
	}

}
