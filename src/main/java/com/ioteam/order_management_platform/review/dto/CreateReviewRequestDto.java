package com.ioteam.order_management_platform.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class CreateReviewRequestDto {
    @NotNull
    private UUID reviewOrderId;
    @NotNull
    @Max(1000)
    private int reviewScore;
    @NotNull
    private String reviewContent;
    private String reviewImageUrl;
    private boolean isPublic;
}
