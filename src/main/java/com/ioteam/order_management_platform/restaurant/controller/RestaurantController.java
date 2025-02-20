package com.ioteam.order_management_platform.restaurant.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.success.SuccessCode;
import com.ioteam.order_management_platform.restaurant.dto.req.CreateRestaurantRequestDto;
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

}
