package com.ioteam.order_management_platform.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.category.execption.CategoryException;
import com.ioteam.order_management_platform.category.repository.CategoryRepository;
import com.ioteam.order_management_platform.district.entity.District;
import com.ioteam.order_management_platform.district.execption.DistrictException;
import com.ioteam.order_management_platform.district.repository.DistrictRepository;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.restaurant.dto.req.CreateRestaurantRequestDto;
import com.ioteam.order_management_platform.restaurant.dto.res.RestaurantResponseDto;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.execption.RestaurantException;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.exception.UserException;
import com.ioteam.order_management_platform.user.repository.UserRepository;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

	private final UserRepository userRepository;
	private final RestaurantRepository restaurantRepository;
	private final DistrictRepository districtRepository;
	private final CategoryRepository categoryRepository;

	private boolean hasManagerOrOwnerRole(UserDetailsImpl userDetails) {
		return userDetails
			.getAuthorities()
			.stream()
			.anyMatch(authority ->
				authority.getAuthority().equals("ROLE_MANAGER") ||
					authority.getAuthority().equals("ROLE_OWNER")
			);
	}

	@Transactional
	public RestaurantResponseDto createRestaurant(CreateRestaurantRequestDto createRestaurantRequestDto,
		UserDetailsImpl userDetails) {

		boolean isAuthorized = hasManagerOrOwnerRole(userDetails);

		if (!isAuthorized) {
			throw new CustomApiException(RestaurantException.NOT_AUTHORIZED_ROLE);
		}

		User user = userRepository.findByUserIdAndDeletedAtIsNull(createRestaurantRequestDto.getResOwnerId())
			.orElseThrow(() -> new CustomApiException(UserException.USER_NOT_FOUND));

		Category category = categoryRepository.findByRcIdAndDeletedAtIsNull(createRestaurantRequestDto.getRcId())
			.orElseThrow(() -> new CustomApiException(CategoryException.CATEGORY_NOT_FOUND));

		District district = districtRepository.findByDistrictIdAndDeletedAtIsNull(
				createRestaurantRequestDto.getResDistrictId())
			.orElseThrow(() -> new CustomApiException(DistrictException.DISTRICT_NOT_FOUND));

		Restaurant restaurant = CreateRestaurantRequestDto.toCategory(
			createRestaurantRequestDto,
			category,
			user,
			district
		);

		Restaurant savedRestaurant = restaurantRepository.save(restaurant);

		return RestaurantResponseDto.fromRestaurant(savedRestaurant);
	}
}
