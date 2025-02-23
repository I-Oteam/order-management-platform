package com.ioteam.order_management_platform.menu.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.menu.dto.req.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.req.UpdateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.res.MenuResponseDto;
import com.ioteam.order_management_platform.menu.service.MenuService;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
public class MenuController {

	private final MenuService menuService;

	@Operation(summary = "상품 등록", description = "상품 수정은 OWNER와 MANAGER, MASTER만 가능")
	@PostMapping()
	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	public ResponseEntity<CommonResponse<MenuResponseDto>> createMenu(
		@RequestBody @Validated CreateMenuRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		MenuResponseDto responseDto = menuService.createMenu(requestDto, userDetails);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/menus/" + responseDto.getRmId().toString())
			.build()
			.toUri();
		return ResponseEntity.created(location)
			.body(new CommonResponse<>(SuccessCode.MENU_CREATE, responseDto));
	}

	@Operation(summary = "특정 가게 상품 전체 조회")
	@GetMapping("restaurant/{restaurant_id}")
	public ResponseEntity<CommonResponse<CommonPageResponse<MenuResponseDto>>> getAllMenu(
		@PathVariable("restaurant_id") UUID restaurantId,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PageableDefault
		@SortDefault.SortDefaults({
			@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
			@SortDefault(sort = "modifiedAt", direction = Sort.Direction.DESC),
			@SortDefault(sort = "rmPrice", direction = Sort.Direction.ASC)
		}) Pageable pageable) {
		CommonPageResponse<MenuResponseDto> responseDto = menuService.getAllMenus(restaurantId, userDetails,
			pageable);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.MENU_LIST_INFO, responseDto));
	}

	@Operation(summary = "상품 상세 조회")
	@GetMapping("/{menu_id}")
	public ResponseEntity<CommonResponse<MenuResponseDto>> getMenuDetail(@PathVariable("menu_id") UUID menuId) {
		MenuResponseDto responseDto = menuService.getMenuDetail(menuId);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.MENU_DETAIL_INFO, responseDto));
	}

	@Operation(summary = "상품 수정", description = "상품 수정은 OWNER와 MANAGER, MASTER만 가능")
	@PatchMapping("/{menu_id}")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	public ResponseEntity<CommonResponse<MenuResponseDto>> modifyMenu(@PathVariable("menu_id") UUID menuId,
		@RequestBody @Validated UpdateMenuRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		MenuResponseDto responseDto = menuService.modifyMenu(menuId, requestDto, userDetails);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.MENU_MODIFY, responseDto));
	}

	@Operation(summary = "상품 숨김 처리", description = "상품 숨김처리는 OWNER와 MANAGER, MASTER만 가능")
	@PatchMapping("/{menu_id}/hidden")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	public ResponseEntity<CommonResponse<MenuResponseDto>> hiddenMenu(@PathVariable("menu_id") UUID menuId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		MenuResponseDto responseDto = menuService.hiddenMenu(menuId, userDetails);
		return ResponseEntity.ok(new CommonResponse<>(SuccessCode.MENU_MODIFY, responseDto));
	}

	@Operation(summary = "상품 삭제", description = "상품 삭제는 OWNER와 MANAGER, MASTER만 가능")
	@DeleteMapping("/{menu_id}")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	public ResponseEntity<CommonResponse<MenuResponseDto>> deleteMenu(@PathVariable("menu_id") UUID menuId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		menuService.deleteMenu(menuId, userDetails);
		return ResponseEntity.noContent().build();
	}
}
