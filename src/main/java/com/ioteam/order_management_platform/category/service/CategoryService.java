package com.ioteam.order_management_platform.category.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.category.dto.req.CreateCategoryRequestDto;
import com.ioteam.order_management_platform.category.dto.res.CategoryResponseDto;
import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.category.execption.CategoryException;
import com.ioteam.order_management_platform.category.repository.CategoryRepository;
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
		return userDetails.getAuthorities()
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
		boolean isManager = hasManagerOrOwnerRole(userDetails);

		// 카테고리 커스텀 익셉션 발생
		if (!isManager) {
			throw new CustomApiException(CategoryException.NOT_AUTHORIZED_ROLE);
		}

		if (categoryRequestDto.getRcName()
			.trim()
			.isEmpty()) {
			throw new CustomApiException(CategoryException.EMPTY_CATEGORY_NAME);
		}

		Category category = categoryRequestDto.toCategory(categoryRequestDto);
		Category savedCategory = categoryRepository.save(category);

		return CategoryResponseDto.fromCategory(savedCategory);
	}

	public CategoryResponseDto readOneCategory(UUID rcId, UserDetailsImpl userDetails) {

		boolean isAuthorized = hasManagerOrOwnerRole(userDetails);

		if (!isAuthorized) {
			throw new CustomApiException(CategoryException.NOT_AUTHORIZED_ROLE);
		}

		Category category = categoryRepository.findByRcIdAndDeletedAtIsNull(rcId)
			.orElseThrow(() -> new CustomApiException(CategoryException.CATEGORY_NOT_FOUND));

		return CategoryResponseDto.fromCategory(category);
	}

	public Page<CategoryResponseDto> readAllCategories(Pageable pageable, UserDetailsImpl userDetails) {

		boolean isAuthorized = hasManagerOrOwnerRole(userDetails);

		if (!isAuthorized) {
			throw new CustomApiException(CategoryException.NOT_AUTHORIZED_ROLE);
		}

		Page<Category> categories = categoryRepository.findAllByDeletedAtIsNull(pageable);

		if (categories.isEmpty()) {
			throw new CustomApiException(CategoryException.CATEGORY_NOT_FOUND);
		}

		return categories.map(CategoryResponseDto::fromCategory);

	}
}
