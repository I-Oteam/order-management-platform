package com.ioteam.order_management_platform.menu.entity;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.menu.dto.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.MenuResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_restaurant_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID rmId;
    @Column(length = 100, nullable = false)
    // TODO: 가게 ID 연관관계 매핑

    private String rmName;
    @Column(nullable = false)
    private BigDecimal rmPrice;
    private String rmImageUrl;
    @Column(length = 100)
    private String rmAiDescription;
    @ColumnDefault("true")
    private Boolean isPublic;
    private LocalDateTime deletedAt;
    private UUID deletedBy;

    public Menu(CreateMenuRequestDto requestDto) {
        this.rmName = requestDto.getRmName();
        this.rmPrice = requestDto.getRmPrice();
        this.rmImageUrl = requestDto.getRmImageUrl();
        this.rmAiDescription = requestDto.getRmAiDescription();
        this.isPublic = requestDto.getIsPublic();
    }

    public MenuResponseDto toResponseDto() {
        return MenuResponseDto
                .builder()
                .rmId(this.rmId)
                .rmName(this.rmName)
                .rmPrice(this.rmPrice)
                .rmImageUrl(this.rmImageUrl)
                .rmAiDescription(this.rmAiDescription)
                .isPublic(this.isPublic)
                .build();
    }
}