package com.ioteam.order_management_platform.restaurant.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.menu.entity.Menu;

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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_restaurant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID resId;
	@Column(length = 100, nullable = false)
	private String resName;
	@Column(length = 100, nullable = false)
	private String resAdress;
	@Column(length = 20, nullable = false)
	private String resPhone;
	private String resImageUrl;
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<Menu> menuList = new ArrayList<>();
}
