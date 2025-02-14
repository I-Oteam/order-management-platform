package com.ioteam.order_management_platform.category.dto;

import com.ioteam.order_management_platform.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {

    private UUID rcId;
    private String rcName;
    private LocalDateTime deleteAt;
    private UUID deleteBy;

    public CategoryResponseDto(Category category) {
        this.rcId = category.getRcId();
        this.rcName = category.getRcName();
        this.deleteAt = category.getDeletedAt();
        this.deleteBy = category.getDeletedBy();
    }
}
