package com.ioteam.order_management_platform.category.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.category.dto.CategoryResponseDto;
import com.ioteam.order_management_platform.category.dto.CreateCategoryRequestDto;
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

	@Transactional
	public CategoryResponseDto createCategory(CreateCategoryRequestDto categoryRequestDto,
		UserDetailsImpl userDetails) {

		// Role이 Manager 인지 검증
		boolean isManager = userDetails.getAuthorities()
			.stream()
			.anyMatch(authority -> authority.getAuthority().equals("ROLE_MANAGER"));

		// 카테고리 커스텀 익셉션 발생
		if (!isManager) {
			throw new CustomApiException(CategoryException.NOT_MANAGER_ROLE);
		}

		if (categoryRequestDto.getRcName()
			.trim()
			.isEmpty()) {
			throw new CustomApiException(CategoryException.EMPTY_CATEGORY_NAME);
		}

		Category category = categoryRepository.save(
			Category.builder()
				.rcName(categoryRequestDto.getRcName())
				.build()
		);

		return CategoryResponseDto.fromCategory(category);
	}
}
