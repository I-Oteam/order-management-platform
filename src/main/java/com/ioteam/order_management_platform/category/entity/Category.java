package com.ioteam.order_management_platform.category.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ioteam.order_management_platform.category.dto.req.UpdateCategoryRequestDto;
import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "p_restaurant_category")
public class Category extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID rcId;

	@Column(nullable = false, unique = true, length = 100)
	private String rcName;

	@OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
	private List<Restaurant> restaurants = new ArrayList<>();

	public void update(UpdateCategoryRequestDto updateCategoryDto) {
		rcName = updateCategoryDto.getRcName();
	}
}
