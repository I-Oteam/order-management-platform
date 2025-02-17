package com.ioteam.order_management_platform.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ReviewSearchCondition {
    private LocalDateTime startCreatedAt;
    private LocalDateTime endCreatedAt;
    private Integer score;
    private Boolean isPublic;
    private Boolean isDeleted;
}
