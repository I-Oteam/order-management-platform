package com.ioteam.order_management_platform.menu.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.menu.dto.req.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.res.MenuListResponseDto;
import com.ioteam.order_management_platform.menu.dto.res.MenuResponseDto;
import com.ioteam.order_management_platform.menu.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
public class MenuController {

	private final MenuService menuService;

	@Operation(summary = "상품 등록")
	@PostMapping()
	@PreAuthorize("hasRole('OWNER')")
	public ResponseEntity<CommonResponse<MenuResponseDto>> createMenu(
		@RequestBody @Validated CreateMenuRequestDto requestDto) {
		MenuResponseDto responseDto = menuService.createMenu(requestDto);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/menus/" + responseDto.getRmId().toString())
			.build()
			.toUri();
		return ResponseEntity.created(location)
			.body(new CommonResponse<>(SuccessCode.MENU_CREATE, responseDto));
	}

	@Operation(summary = "상품 전체 조회")
	@GetMapping("/{restaurant_id}")
	public ResponseEntity<CommonResponse<MenuListResponseDto>> getAllMenu(
		@PathVariable("restaurant_id") UUID restaurantId) {
		MenuListResponseDto responseDto = menuService.getAllMenus(restaurantId);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.MENU_LIST_INFO, responseDto));
	}
}
