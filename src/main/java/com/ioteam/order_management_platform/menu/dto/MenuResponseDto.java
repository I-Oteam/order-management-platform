package com.ioteam.order_management_platform.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class MenuResponseDto {
    private UUID rmId;
    private String rmName;
    private BigDecimal rmPrice;
    private String rmImageUrl;
    private String rmAiDescription;
    private Boolean isPublic;
}
