package com.ioteam.order_management_platform.menu.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.menu.dto.req.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.req.UpdateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.res.MenuResponseDto;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.exception.MenuException;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.execption.RestaurantException;
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
	public MenuResponseDto createMenu(CreateMenuRequestDto requestDto, UserDetailsImpl userDetails) {
		Restaurant restaurant = restaurantRepository.findByResIdAndDeletedAtIsNull(requestDto.getResId())
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_RESTAURANT_ID));
		if (!restaurant.isOwner(userDetails)) {
			throw new CustomApiException(RestaurantException.INSUFFICIENT_PERMISSION);
		}
		Menu menu = requestDto.toEntity(requestDto, restaurant);
		Menu savedMenu = menuRepository.save(menu);
		return MenuResponseDto.fromEntity(savedMenu);
	}

	public CommonPageResponse<MenuResponseDto> getAllMenus(UUID resId, UserDetailsImpl userDetails,
		Pageable pageable) {
		validRestaurantExist(resId);
		Page<MenuResponseDto> menuList = menuRepository.findMenusByResIdAndRole(resId, userDetails, pageable);
		return new CommonPageResponse<>(menuList);
	}

	public MenuResponseDto getMenuDetail(UUID menuId) {
		Menu menu = menuRepository.findByRmIdAndDeletedAtIsNull(menuId)
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_MENU));
		return MenuResponseDto.fromEntity(menu);
	}

	@Transactional
	public MenuResponseDto modifyMenu(UUID menuId, UpdateMenuRequestDto requestDto, UserDetailsImpl userDetails) {
		Menu menu = menuRepository.findByRmIdAndDeletedAtIsNull(menuId)
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_MENU));
		if (!hasUpdateAuthority(userDetails, menu)) {
			throw new CustomApiException(MenuException.NOT_AUTHORIZED_FOR_MENU);
		}
		Menu updatedMenu = menu.updateMenu(requestDto);
		return MenuResponseDto.fromEntity(updatedMenu);
	}

	@Transactional
	public MenuResponseDto hiddenMenu(UUID menuId, UserDetailsImpl userDetails) {
		Menu menu = menuRepository.findByRmIdAndDeletedAtIsNull(menuId)
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_MENU));
		if (!hasUpdateAuthority(userDetails, menu)) {
			throw new CustomApiException(MenuException.NOT_AUTHORIZED_FOR_MENU);
		}
		Menu hiddenMenu = menu.hiddenMenu();
		return MenuResponseDto.fromEntity(hiddenMenu);
	}

	@Transactional
	public void deleteMenu(UUID menuId, UserDetailsImpl userDetails) {
		Menu menu = menuRepository.findByRmIdAndDeletedAtIsNull(menuId)
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_MENU));
		if (!hasUpdateAuthority(userDetails, menu)) {
			throw new CustomApiException(MenuException.NOT_AUTHORIZED_FOR_MENU);
		}
		menu.softDelete(userDetails.getUserId());
	}

	private boolean hasUpdateAuthority(UserDetailsImpl userDetails, Menu menu) {
		UserRoleEnum role = userDetails.getRole();
		if (role.equals(UserRoleEnum.MANAGER) || role.equals(UserRoleEnum.MASTER)) {
			return true;
		} else if (role.equals(UserRoleEnum.OWNER)) {
			return hasPermissionForMenu(userDetails, menu);
		}
		return false;
	}

	private boolean hasPermissionForMenu(UserDetailsImpl userDetails, Menu menu) {
		UUID requestUser = userDetails.getUserId();
		UUID ownerUser = menuRepository.getRestaurantOwnerId(menu.getRmId());
		log.info(
			"Updating menu with menuId: " + menu.getRmId() + " with ownerUser: " + ownerUser + " with requestUser: "
				+ requestUser);
		if (requestUser.equals(ownerUser)) {
			return true;
		}
		return false;
	}

	private void validRestaurantExist(UUID restaurantId) {
		if (!restaurantRepository.existsByResIdAndDeletedAtIsNull(restaurantId)) {
			throw new CustomApiException(MenuException.INVALID_RESTAURANT_ID);
		}
	}
}
