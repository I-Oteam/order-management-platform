package com.ioteam.order_management_platform.restaurant.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.category.execption.CategoryException;
import com.ioteam.order_management_platform.category.repository.CategoryRepository;
import com.ioteam.order_management_platform.district.entity.District;
import com.ioteam.order_management_platform.district.execption.DistrictException;
import com.ioteam.order_management_platform.district.repository.DistrictRepository;
import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.restaurant.dto.req.CreateRestaurantRequestDto;
import com.ioteam.order_management_platform.restaurant.dto.req.ModifyRestaurantRequestDto;
import com.ioteam.order_management_platform.restaurant.dto.res.RestaurantResponseDto;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.entity.RestaurantScore;
import com.ioteam.order_management_platform.restaurant.execption.RestaurantException;
import com.ioteam.order_management_platform.restaurant.execption.RestaurantScoreException;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantScoreRepository;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.exception.UserException;
import com.ioteam.order_management_platform.user.repository.UserRepository;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

	private final UserRepository userRepository;
	private final RestaurantRepository restaurantRepository;
	private final DistrictRepository districtRepository;
	private final CategoryRepository categoryRepository;
	private final RestaurantScoreRepository restaurantScoreRepository;

	private boolean hasManagerOrOwnerRole(UserDetailsImpl userDetails) {
		return userDetails
			.getAuthorities()
			.stream()
			.anyMatch(authority ->
				authority.getAuthority().equals("ROLE_MANAGER") ||
					authority.getAuthority().equals("ROLE_OWNER")
			);
	}

	private boolean hasPermissionForRestaurant(UserDetailsImpl userDetails, Restaurant restaurant) {

		if (userDetails.getRole().equals(UserRoleEnum.MANAGER))
			return true;
		else if (userDetails.getRole().equals(UserRoleEnum.OWNER))
			return userDetails.getUserId().equals(restaurant.getOwner().getUserId());

		return false;
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
		// RestaurantScore restaurantScore = restaurantScoreRepository.findByRestaurantResIdAndDeletedAtIsNull(
		// 		savedRestaurant.getResId())
		// 	.orElseThrow(() -> new CustomApiException(RestaurantScoreException.NOT_FOUND_SCORE));

		return RestaurantResponseDto.fromRestaurant(savedRestaurant);
	}

	public RestaurantResponseDto searchOneRestaurant(UUID resId) {

		Restaurant targetRestaurant = restaurantRepository.findByResIdWithScoreAndDeletedAtIsNull(resId)
			.orElseThrow(() -> new CustomApiException(RestaurantException.NOT_FOUND_RESTAURANT));

		return RestaurantResponseDto.fromRestaurant(targetRestaurant);
	}

	@Transactional
	public void softDeleteRestaurant(UUID resId, UserDetailsImpl userDetails) {

		Restaurant targetRestaurant = restaurantRepository.findByResIdWithScoreAndDeletedAtIsNull(resId)
			.orElseThrow(() -> new CustomApiException(RestaurantException.NOT_FOUND_RESTAURANT));

		RestaurantScore restaurantScore = restaurantScoreRepository.findByRestaurantResIdAndDeletedAtIsNull(resId)
			.orElseThrow(() -> new CustomApiException(RestaurantScoreException.NOT_FOUND_SCORE));

		// 해당 유저가 가게 주인인지 판별
		if (!hasPermissionForRestaurant(userDetails, targetRestaurant)) {
			throw new CustomApiException(RestaurantException.INSUFFICIENT_PERMISSION);
		}

		targetRestaurant.softDelete(userDetails.getUserId());
		restaurantScore.softDelete(userDetails.getUserId());
	}

	@Transactional
	public RestaurantResponseDto updateRestaurant(UUID resId,
		ModifyRestaurantRequestDto modifyRestaurantRequestDto,
		UserDetailsImpl userDetails) {

		Restaurant targetRestaurant = restaurantRepository.findByResIdWithScoreAndDeletedAtIsNull(resId)
			.orElseThrow(() -> new CustomApiException(RestaurantException.NOT_FOUND_RESTAURANT));

		User modifiedUser = null;
		if (modifyRestaurantRequestDto.getResOwnerId() != null) {
			modifiedUser = userRepository.findByUserIdAndDeletedAtIsNull(modifyRestaurantRequestDto.getResOwnerId())
				.orElseThrow(() -> new CustomApiException(UserException.USER_NOT_FOUND));
			// Enum으로 쓰는게 좋다 >> 값에 제한을 ENUM으로 주기 때문에
			if (!modifiedUser.getRole().equals(UserRoleEnum.OWNER)) {
				throw new CustomApiException(RestaurantException.NOT_AUTHORIZED_ROLE);
			}
		}

		Category modifiedCategory = null;
		if (modifyRestaurantRequestDto.getRcId() != null) {
			modifiedCategory = categoryRepository.findByRcIdAndDeletedAtIsNull(modifyRestaurantRequestDto.getRcId())
				.orElseThrow(() -> new CustomApiException(CategoryException.CATEGORY_NOT_FOUND));
		}

		District modifiedDistrict = null;
		if (modifyRestaurantRequestDto.getResDistrictId() != null) {
			modifiedDistrict = districtRepository.findByDistrictIdAndDeletedAtIsNull(
					modifyRestaurantRequestDto.getResDistrictId())
				.orElseThrow(() -> new CustomApiException(DistrictException.DISTRICT_NOT_FOUND));
		}

		if (!hasPermissionForRestaurant(userDetails, targetRestaurant)) {
			throw new CustomApiException(RestaurantException.INSUFFICIENT_PERMISSION);
		}

		targetRestaurant.update(modifyRestaurantRequestDto, modifiedUser, modifiedCategory, modifiedDistrict);

		return RestaurantResponseDto.fromRestaurant(targetRestaurant);
	}

	public CommonPageResponse<RestaurantResponseDto> searchAllRestaurant(Pageable pageable) {

		Page<Restaurant> restaurants = restaurantRepository.findAllWithScoreByDeletedAtIsNull(pageable);

		if (restaurants.isEmpty()) {
			throw new CustomApiException(RestaurantException.NOT_FOUND_RESTAURANT);
		}

		Page<RestaurantResponseDto> restaurantResponseDtoPage = restaurants.map(RestaurantResponseDto::fromRestaurant);

		return new CommonPageResponse<>(restaurantResponseDtoPage);
	}

	// public CommonPageResponse<RestaurantResponseDto> searchRestaurantsByScoreRange(BigDecimal score,
	// 	Pageable pageable) {
	//
	// 	// min이상 max미만 검색
	// 	BigDecimal minScore = score.setScale(1, RoundingMode.FLOOR);
	// 	BigDecimal maxScore = minScore.add(BigDecimal.valueOf(1));
	//
	// 	Page<Restaurant> restaurants = restaurantRepository.findRestaurantsByScoreRangeAndDeletedAtIsNull(minScore,
	// 		maxScore, pageable);
	//
	// 	if (restaurants.isEmpty()) {
	// 		throw new CustomApiException(RestaurantException.NOT_FOUND_RESTAURANT);
	// 	}
	//
	// 	Page<RestaurantResponseDto> restaurantResponseDtoPage = restaurants.map(RestaurantResponseDto::fromRestaurant);
	//
	// 	return new CommonPageResponse<>(restaurantResponseDtoPage);
	// }
	//
	// public CommonPageResponse<RestaurantResponseDto> searchAllRestaurantsSortedByScore(Pageable pageable) {
	//
	// 	Page<Restaurant> restaurants = restaurantRepository.findAllWithScoreSortedByScoreDescAndDeletedAtIsNull(
	// 		pageable);
	//
	// 	if (restaurants.isEmpty()) {
	// 		throw new CustomApiException(RestaurantException.NOT_FOUND_RESTAURANT);
	// 	}
	//
	// 	Page<RestaurantResponseDto> restaurantResponseDtoPage = restaurants.map(RestaurantResponseDto::fromRestaurant);
	//
	// 	return new CommonPageResponse<>(restaurantResponseDtoPage);
	// }

	// JPQL 서비스 2개로직을 > QueryDSL 1개로 변경
	public CommonPageResponse<RestaurantResponseDto> searchRestaurants(BigDecimal score, Pageable pageable) {
		Page<Restaurant> restaurants = restaurantRepository.searchRestaurants(score, pageable);

		if (restaurants.isEmpty()) {
			throw new CustomApiException(RestaurantException.NOT_FOUND_RESTAURANT);
		}

		Page<RestaurantResponseDto> restaurantResponseDtoPage = restaurants.map(RestaurantResponseDto::fromRestaurant);
		return new CommonPageResponse<>(restaurantResponseDtoPage);
	}

	public CommonPageResponse<RestaurantResponseDto> searchCategoryRestaurants(String rcName, Pageable pageable) {

		Page<Restaurant> restaurants = restaurantRepository.findAllByCategory_RcNameAndDeletedAtIsNull(rcName,
			pageable);

		if (restaurants.isEmpty()) {
			throw new CustomApiException(RestaurantException.NOT_FOUND_RESTAURANT);
		}

		Page<RestaurantResponseDto> restaurantResponseDtoPage = restaurants.map(RestaurantResponseDto::fromRestaurant);

		return new CommonPageResponse<>(restaurantResponseDtoPage);
	}
}
