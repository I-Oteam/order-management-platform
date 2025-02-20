package com.ioteam.order_management_platform.restaurant.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.district.entity.District;
import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_restaurant",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_res_phone", columnNames = "res_phone")
	})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID resId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "res_owner_id")
	private User owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "res_category_id")
	private Category category;

	// 이곳에 지역 연관관계 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "res_district_id")
	private District district;

	@Column(length = 100, nullable = false)
	private String resName;

	@Column(length = 100, nullable = false)
	private String resAddress;

	@Column(length = 20, nullable = false)
	private String resPhone;

	private String resImageUrl;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<Menu> menuList = new ArrayList<>();
}
