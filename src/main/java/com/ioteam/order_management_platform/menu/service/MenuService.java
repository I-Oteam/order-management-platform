package com.ioteam.order_management_platform.menu.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.menu.dto.req.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.req.UpdateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.res.MenuListResponseDto;
import com.ioteam.order_management_platform.menu.dto.res.MenuResponseDto;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.exception.MenuException;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

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
		Restaurant restaurant = restaurantRepository.findById(requestDto.getResId())
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_RESTAURANT_ID));
		Menu menu = requestDto.toEntity(requestDto, restaurant);
		Menu savedMenu = menuRepository.save(menu);
		return MenuResponseDto.fromEntity(savedMenu);
	}

	public MenuListResponseDto getAllMenus(UUID restaurantId) {
		validRestaurantExist(restaurantId);
		List<Menu> menuList = menuRepository.findByRestaurant_ResId(restaurantId);
		// TODO: isPublic 조회 조건에 추가
		return MenuListResponseDto.of(menuList);
	}

	public MenuResponseDto getMenuDetail(UUID menuId) {
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_MENU));
		return MenuResponseDto.fromEntity(menu);
	}

	@Transactional
	public MenuResponseDto modifyMenu(UUID menuId, UpdateMenuRequestDto requestDto, UserDetailsImpl userDetails) {
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_MENU));
		hasModificationPermission(userDetails, menu);
		Menu updatedMenu = menu.updateMenu(requestDto);
		return MenuResponseDto.fromEntity(updatedMenu);
	}

	private void hasModificationPermission(UserDetailsImpl userDetails, Menu menu) {
		UserRoleEnum role = userDetails.getRole();
		if (role.equals(UserRoleEnum.MANAGER)) {
			return;
		}
		if (role.equals(UserRoleEnum.OWNER)) {
			UUID requestUser = userDetails.getUserId();
			UUID ownerUser = menuRepository.getRestaurantOwnerId(menu.getRmId());
			log.info(
				"Updating menu with menuId: " + menu.getRmId() + " with ownerUser: " + ownerUser + " with requestUser: "
					+ requestUser);
			if (requestUser.equals(ownerUser)) {
				return;
			}
		}
		throw new CustomApiException(MenuException.INVALID_MODIFY_ROLE);
	}

	private void validRestaurantExist(UUID restaurantId) {
		if (!restaurantRepository.existsById(restaurantId)) {
			throw new CustomApiException(MenuException.INVALID_RESTAURANT_ID);
		}
	}
}
