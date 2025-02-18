package com.ioteam.order_management_platform.menu.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_restaurant_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Menu extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID rmId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "res_id")
	private Restaurant restaurant;
	@Column(length = 100, nullable = false)
	private String rmName;
	@Column(nullable = false)
	private BigDecimal rmPrice;
	private String rmImageUrl;
	@Column(length = 100)
	private String rmDescription;
	@ColumnDefault("true")
	private Boolean isPublic;

}