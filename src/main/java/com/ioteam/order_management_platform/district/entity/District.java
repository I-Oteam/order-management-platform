package com.ioteam.order_management_platform.district.entity;

import java.util.List;
import java.util.UUID;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_district")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class District extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID districtId;

	@Column(length = 20, nullable = false)
	private String districtSigunguCode;

	@Column(length = 100, nullable = false)
	private String districtSigunguName;

	@Column(length = 20, nullable = false)
	private String districtDongName;

	@OneToMany(mappedBy = "district", cascade = CascadeType.MERGE)
	private List<Restaurant> restaurants;

}
