package com.ioteam.order_management_platform.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ReviewSearchCondition {
    private LocalDateTime startCreatedAt;
    private LocalDateTime endCreatedAt;
    private Integer score;
}
