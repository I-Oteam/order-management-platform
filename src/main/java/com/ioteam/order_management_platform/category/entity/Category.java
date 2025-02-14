package com.ioteam.order_management_platform.category.entity;

import com.ioteam.order_management_platform.category.dto.CategoryRequestDto;
import com.ioteam.order_management_platform.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_restaurant_category")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID rcId;

    @Column(nullable = false)
    private String rcName;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private UUID deletedBy;

    public Category(CategoryRequestDto categoryRequestDto) {
        this.rcName = categoryRequestDto.getRcName();
    }
}
