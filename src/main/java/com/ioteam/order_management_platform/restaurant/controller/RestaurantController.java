package com.ioteam.order_management_platform.restaurant.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.restaurant.dto.req.CreateRestaurantRequestDto;
import com.ioteam.order_management_platform.restaurant.dto.req.ModifyRestaurantRequestDto;
import com.ioteam.order_management_platform.restaurant.dto.res.RestaurantResponseDto;
import com.ioteam.order_management_platform.restaurant.service.RestaurantService;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "가게", description = "가게 API")
public class RestaurantController {

	private final RestaurantService restaurantService;

	@PostMapping("/restaurants")
	@Operation(summary = "가게 등록", description = "가게 등록은 'MANAGER' , 'OWNER' 만 가능")
	public ResponseEntity<CommonResponse<RestaurantResponseDto>> createRestaurant(
		@RequestBody @Validated CreateRestaurantRequestDto createRestaurantRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		RestaurantResponseDto restaurantResponseDto = restaurantService.createRestaurant(createRestaurantRequestDto,
			userDetails);

		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.path("/api/restaurants")
			.build()
			.toUri();

		return ResponseEntity
			.created(location)
			.body(new CommonResponse<>(SuccessCode.RESTAURANT_CREATE, restaurantResponseDto));
	}

	@GetMapping("/restaurants/{resId}")
	@Operation(summary = "가게 단건 조회", description = "아무나 조회 가능")
	public ResponseEntity<CommonResponse<RestaurantResponseDto>> getOneRestaurant(UUID resId) {

		RestaurantResponseDto restaurantResponseDto = restaurantService.searchOneRestaurant(resId);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(SuccessCode.RESTAURANT_ONE_SEARCH, restaurantResponseDto));
	}

	@DeleteMapping("/restaurants/{resId}")
	@Operation(summary = "가게 소프트 삭제", description = "가게 삭제는 'MANAGER' , 'OWNER' 만 가능")
	public ResponseEntity<CommonResponse<RestaurantResponseDto>> softDeleteRestaurant(
		@PathVariable UUID resId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {

		restaurantService.softDeleteRestaurant(resId, userDetails);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(SuccessCode.RESTAURANT_DELETE, null));
	}

	@PatchMapping("/restaurants/{resId}")
	@Operation(summary = "가게 정보 수정", description = "가게 수정은 'MANAGER' , 'OWNER' 만 가능")
	public ResponseEntity<CommonResponse<RestaurantResponseDto>> updateRestaurant(
		@PathVariable UUID resId,
		@RequestBody @Validated ModifyRestaurantRequestDto modifyRestaurantRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {

		RestaurantResponseDto restaurantResponseDto = restaurantService.updateRestaurant(resId,
			modifyRestaurantRequestDto, userDetails);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(SuccessCode.RESTAURANT_MODIFY, restaurantResponseDto));
	}

	@GetMapping("/restaurants/all")
	@Operation(summary = "모든 가게 조회", description = "아무나 조회 가능")
	public ResponseEntity<CommonResponse<CommonPageResponse<RestaurantResponseDto>>> getAllRestaurants(
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
	) {
		CommonPageResponse<RestaurantResponseDto> restaurants = restaurantService.searchAllRestaurant(pageable);

		return ResponseEntity
			.ok()
			.body(new CommonResponse<>(SuccessCode.RESTAURANT_SEARCH, restaurants));
	}

	@GetMapping("/restaurants/score")
	@Operation(summary = "별점별 가게 조회", description = "아무나 조회 가능\n별점순으로 가게를 조회하거나 특정별점 조회 가능")
	public ResponseEntity<CommonResponse<CommonPageResponse<RestaurantResponseDto>>> getRestaurantsByScore(
		@RequestParam(value = "score", required = false) BigDecimal score,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
	) {

		CommonPageResponse<RestaurantResponseDto> restaurants;

		if (score != null) {
			restaurants = restaurantService.searchRestaurantsByScoreRange(score, pageable);
		} else {
			restaurants = restaurantService.searchAllRestaurantsSortedByScore(pageable);
		}

		return ResponseEntity.ok()
			.body(new CommonResponse<>(SuccessCode.RESTAURANT_SEARCH, restaurants));
	}

	@GetMapping("/restaurants/category")
	@Operation(summary = "카테고리별 가게 조회", description = "아무나 조회 가능\n")
	public ResponseEntity<CommonResponse<CommonPageResponse<RestaurantResponseDto>>> getRestaurantsByCategory(
		@RequestParam(value = "category", required = true) UUID rcId,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
	) {

		CommonPageResponse<RestaurantResponseDto> restaurants = restaurantService.searchCategoryRestaurants(rcId,
			pageable);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(SuccessCode.RESTAURANT_SEARCH, restaurants));
	}
}
