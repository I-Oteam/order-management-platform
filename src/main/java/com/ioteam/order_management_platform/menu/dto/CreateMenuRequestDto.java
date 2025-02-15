package com.ioteam.order_management_platform.menu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class CreateMenuRequestDto {

    @NotNull
    private UUID resId;
    @Length(max = 100)
    @NotNull
    private String rmName;
    @NotNull
    private BigDecimal rmPrice;
    private String rmImageUrl;
    @Length(max = 100)
    private String rmAiDescription;
    private Boolean isPublic;

}
