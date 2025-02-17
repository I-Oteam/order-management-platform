package com.ioteam.order_management_platform.review.dto;

import com.ioteam.order_management_platform.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class AdminReviewResponseDto {

    private final UUID reviewId;
    private final ReviewUserResponseDto userResponseDto;
    private final int reviewScore;
    private final String reviewContent;
    private final String reviewImageUrl;
    private final UUID reviewOrderId;
    private final Boolean isPublic;
    private final LocalDateTime createdAt;
    private final UUID createdBy;
    private final LocalDateTime modifiedAt;
    private final UUID modifiedBy;
    private final LocalDateTime deletedAt;
    private final UUID deletedBy;

    public static AdminReviewResponseDto from(Review review) {
        return AdminReviewResponseDto
                .builder()
                .reviewId(review.getReviewId())
                //.userResponseDto(this.user.toReviewUserResponseDto())
                .reviewScore(review.getReviewScore())
                .reviewContent(review.getReviewContent())
                .reviewImageUrl(review.getReviewImageUrl())
                .isPublic(review.getIsPublic())
                .createdAt(review.getCreatedAt())
                .createdBy(review.getCreatedBy())
                .modifiedAt(review.getModifiedAt())
                .modifiedBy(review.getModifiedBy())
                .deletedAt(review.getDeletedAt())
                .deletedBy(review.getDeletedBy())
                .build();
    }
}
