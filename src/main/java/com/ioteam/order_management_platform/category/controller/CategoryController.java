package com.ioteam.order_management_platform.category.controller;

import com.ioteam.order_management_platform.category.dto.CategoryRequestDto;
import com.ioteam.order_management_platform.category.dto.CategoryResponseDto;
import com.ioteam.order_management_platform.category.service.CategoryService;
import com.ioteam.order_management_platform.global.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<CommonResponse<CategoryResponseDto>> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {

        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/categories")
                .build()
                .toUri();

        return ResponseEntity.created(location)
                .body(new CommonResponse<CategoryResponseDto>("카테고리 생성 완료" , categoryResponseDto));

    }

}
