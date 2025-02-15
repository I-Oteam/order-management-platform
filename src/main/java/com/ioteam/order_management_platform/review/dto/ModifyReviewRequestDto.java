package com.ioteam.order_management_platform.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class ModifyReviewRequestDto {
    @NotNull
    @Max(5)
    private int reviewScore;
    @NotNull
    @Length(max = 1000)
    private String reviewContent;
    private String reviewImageUrl;
    @NotNull
    private Boolean isPublic;
}
