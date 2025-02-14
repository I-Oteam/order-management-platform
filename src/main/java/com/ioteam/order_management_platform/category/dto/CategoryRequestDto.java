package com.ioteam.order_management_platform.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    private String rcName;
    private LocalDateTime deletedAt;
    private UUID deletedBy;

}
