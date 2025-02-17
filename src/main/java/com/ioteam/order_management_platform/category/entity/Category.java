package com.ioteam.order_management_platform.category.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.category.dto.CreateCategoryRequestDto;
import com.ioteam.order_management_platform.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_restaurant_category")
public class Category extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID rcId;

	@Column(nullable = false, unique = true, length = 100)
	private String rcName;

	@Column
	private LocalDateTime deletedAt;

	@Column
	private UUID deletedBy;

	public Category(CreateCategoryRequestDto categoryRequestDto) {
		this.rcName = categoryRequestDto.getRcName();
	}
}
