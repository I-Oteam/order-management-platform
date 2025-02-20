package com.ioteam.order_management_platform.category.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.category.dto.req.CreateCategoryRequestDto;
import com.ioteam.order_management_platform.category.dto.req.UpdateCategoryRequestDto;
import com.ioteam.order_management_platform.category.dto.res.CategoryResponseDto;
import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.category.execption.CategoryException;
import com.ioteam.order_management_platform.category.repository.CategoryRepository;
import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

	private final CategoryRepository categoryRepository;

	// 중복된 검증 로직 개선(괜찮은지?)
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
	public CategoryResponseDto createCategory(CreateCategoryRequestDto categoryRequestDto,
		UserDetailsImpl userDetails) {

		// Role이 Manager 인지 검증
		boolean isAuthorized = hasManagerOrOwnerRole(userDetails);

		// 카테고리 커스텀 익셉션 발생
		if (!isAuthorized) {
			throw new CustomApiException(CategoryException.NOT_AUTHORIZED_ROLE);
		}

		if (categoryRequestDto
			.getRcName()
			.trim()
			.isEmpty()) {
			throw new CustomApiException(CategoryException.EMPTY_CATEGORY_NAME);
		}

		// 삭제되었는데 다시 부활시키는 방향도?
		if (categoryRepository.existsByRcNameAndDeletedAtIsNull(
			categoryRequestDto
				.getRcName()
				.trim()
		)
		) {
			throw new CustomApiException(CategoryException.DUPLICATE_CATEGORY_NAME);
		}

		Category category = CreateCategoryRequestDto.toCategory(categoryRequestDto);
		Category savedCategory = categoryRepository.save(category);

		return CategoryResponseDto.fromCategory(savedCategory);
	}

	public CategoryResponseDto readOneCategory(UUID rcId, UserDetailsImpl userDetails) {

		boolean isAuthorized = hasManagerOrOwnerRole(userDetails);

		if (!isAuthorized) {
			throw new CustomApiException(CategoryException.NOT_AUTHORIZED_ROLE);
		}

		Category category = categoryRepository
			.findByRcIdAndDeletedAtIsNull(rcId)
			.orElseThrow(
				() -> new CustomApiException(CategoryException.CATEGORY_NOT_FOUND)
			);

		return CategoryResponseDto.fromCategory(category);
	}

	public CommonPageResponse<CategoryResponseDto> readAllCategories(Pageable pageable, UserDetailsImpl userDetails) {

		boolean isAuthorized = hasManagerOrOwnerRole(userDetails);

		if (!isAuthorized) {
			throw new CustomApiException(CategoryException.NOT_AUTHORIZED_ROLE);
		}

		Page<Category> categories = categoryRepository.findAllByDeletedAtIsNull(pageable);

		if (categories.isEmpty()) {
			throw new CustomApiException(CategoryException.CATEGORY_NOT_FOUND);
		}

		Page<CategoryResponseDto> categoryResponseDtoPage = categories.map(CategoryResponseDto::fromCategory);

		return new CommonPageResponse<>(categoryResponseDtoPage);

	}

	@Transactional
	public CategoryResponseDto updateCategory(UUID rcId, UpdateCategoryRequestDto updateCategoryDto,
		UserDetailsImpl userDetails) {

		boolean isAuthorized = hasManagerOrOwnerRole(userDetails);

		if (!isAuthorized) {
			throw new CustomApiException(CategoryException.NOT_AUTHORIZED_ROLE);
		}

		Category targetCategory = categoryRepository.findByRcIdAndDeletedAtIsNull(rcId)
			.orElseThrow(() -> new CustomApiException(CategoryException.CATEGORY_NOT_FOUND));

		if (updateCategoryDto
			.getRcName()
			.trim()
			.isEmpty()) {
			throw new CustomApiException(CategoryException.EMPTY_CATEGORY_NAME);
		}

		targetCategory.update(updateCategoryDto);

		return CategoryResponseDto.fromCategory(targetCategory);
	}

	@Transactional
	public void softDeleteCategory(UUID rcId, UserDetailsImpl userDetails) {

		boolean isAuthorized = hasManagerOrOwnerRole(userDetails);

		if (!isAuthorized) {
			throw new CustomApiException(CategoryException.NOT_AUTHORIZED_ROLE);
		}

		Category targetCategory = categoryRepository.findByRcIdAndDeletedAtIsNull(rcId)
			.orElseThrow(() -> new CustomApiException(CategoryException.CATEGORY_NOT_FOUND));

		targetCategory.softDelete(userDetails.getUserId());
	}
}
