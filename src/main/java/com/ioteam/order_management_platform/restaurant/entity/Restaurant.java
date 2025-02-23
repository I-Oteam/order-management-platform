package com.ioteam.order_management_platform.restaurant.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.ioteam.order_management_platform.category.entity.Category;
import com.ioteam.order_management_platform.district.entity.District;
import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.restaurant.dto.req.ModifyRestaurantRequestDto;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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

	// 식당이 생성될때 score 엔티티도 같이 생성
	@OneToOne(mappedBy = "restaurant", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private RestaurantScore restaurantScore;

	@PrePersist
	public void createRestaurantScore() {
		if (this.restaurantScore == null) {
			this.restaurantScore = new RestaurantScore(this, BigDecimal.ZERO); // 기본 값 0으로 설정
		}
	}

	public void update(ModifyRestaurantRequestDto modifyRestaurantRequestDto, User modifiedUser,
		Category modifiedCategory, District modifiedDistrict) {

		// 트러블 슈팅
		Optional.ofNullable(modifyRestaurantRequestDto.getResName()).ifPresent(value -> this.resName = value);
		Optional.ofNullable(modifyRestaurantRequestDto.getResAddress()).ifPresent(value -> this.resAddress = value);
		Optional.ofNullable(modifyRestaurantRequestDto.getResPhone()).ifPresent(value -> this.resPhone = value);
		Optional.ofNullable(modifyRestaurantRequestDto.getResImageUrl()).ifPresent(value -> this.resImageUrl = value);

		Optional.ofNullable(modifiedUser).ifPresent(value -> this.owner = value);
		Optional.ofNullable(modifiedCategory).ifPresent(value -> this.category = value);
		Optional.ofNullable(modifiedDistrict).ifPresent(value -> this.district = value);

	}

	public boolean isOwner(UserDetailsImpl userDetails) {
		if (Set.of(UserRoleEnum.MANAGER, UserRoleEnum.MASTER).contains(userDetails.getRole()))
			return true;
		if (userDetails.getRole().equals(UserRoleEnum.OWNER))
			return userDetails.getUserId().equals(this.getOwner().getUserId());
		return false;
	}
}
