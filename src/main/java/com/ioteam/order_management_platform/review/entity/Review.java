package com.ioteam.order_management_platform.review.entity;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ReviewResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;
    private int reviewScore;
    @Column(length = 1000)
    private String reviewContent;
    @Column(length = 100)
    private String reviewImageUrl;
    private Boolean isPublic;
    private LocalDateTime deletedAt;
    private UUID deletedBy;

    public Review(CreateReviewRequestDto requestDto) {
        this.reviewScore = requestDto.getReviewScore();
        this.reviewContent = requestDto.getReviewContent();
        this.reviewImageUrl = requestDto.getReviewImageUrl();
        this.isPublic = requestDto.getIsPublic();
    }

    public ReviewResponseDto toResponseDto() {
        return ReviewResponseDto
                .builder()
                .reviewId(this.reviewId)
                .reviewScore(this.reviewScore)
                .reviewContent(this.reviewContent)
                .reviewImageUrl(this.reviewImageUrl)
                //.reviewOrderId(this.)
                .isPublic(this.isPublic)
                .build();
    }
}
