package com.ioteam.order_management_platform.category.service;

import com.ioteam.order_management_platform.category.dto.CategoryRequestDto;
import com.ioteam.order_management_platform.category.dto.CategoryResponseDto;
import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.save(new Category(categoryRequestDto));

        return new CategoryResponseDto(category);
    }
}
