package com.ioteam.order_management_platform.menu.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.menu.dto.req.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.res.MenuListResponseDto;
import com.ioteam.order_management_platform.menu.dto.res.MenuResponseDto;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.exception.MenuException;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

	private final MenuRepository menuRepository;
	private final RestaurantRepository restaurantRepository;

	@Transactional
	public MenuResponseDto createMenu(CreateMenuRequestDto requestDto) {
		Menu menu = requestDto.toEntity(requestDto);
		Menu savedMenu = menuRepository.save(menu);
		return MenuResponseDto.fromEntity(savedMenu);
	}

	public MenuListResponseDto getAllMenus(UUID restaurantId) {
		restaurantRepository.findById(restaurantId)
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_RESTAURANT_ID));
		List<Menu> menuList = menuRepository.findByRestaurant_ResId(restaurantId);
		return MenuListResponseDto.of(menuList);
	}
}
