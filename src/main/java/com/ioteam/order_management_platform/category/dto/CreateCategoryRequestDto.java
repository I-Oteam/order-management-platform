package com.ioteam.order_management_platform.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryRequestDto {

    @NotNull
    @Length(max = 100)
    private String rcName;

}
