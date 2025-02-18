package com.ioteam.order_management_platform.review.entity;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.review.dto.CreateReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ModifyReviewRequestDto;
import com.ioteam.order_management_platform.review.dto.ReviewResponseDto;
import com.ioteam.order_management_platform.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_review")
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_user_id")
    private User user;

    private int reviewScore;

    @Column(length = 1000)
    private String reviewContent;

    @Column(length = 100)
    private String reviewImageUrl;

    @ColumnDefault("true")
    private Boolean isPublic;

    private LocalDateTime deletedAt;

    private UUID deletedBy;

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = UUID.randomUUID();
    }

    public void modify(ModifyReviewRequestDto requestDto) {
        this.reviewScore = requestDto.getReviewScore();
        this.reviewContent = requestDto.getReviewContent();
        this.reviewImageUrl = requestDto.getReviewImageUrl();
        this.isPublic = requestDto.getIsPublic();
    }
}
