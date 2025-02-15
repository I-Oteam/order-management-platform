package com.ioteam.order_management_platform.category.dto;

import com.ioteam.order_management_platform.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {

    private UUID rcId;
    private String rcName;

    public CategoryResponseDto(Category category) {
        this.rcId = category.getRcId();
        this.rcName = category.getRcName();
    }
}
