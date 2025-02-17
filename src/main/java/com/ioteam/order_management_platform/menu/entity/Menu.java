package com.ioteam.order_management_platform.menu.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import com.ioteam.order_management_platform.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	// TODO: 가게 연관관계 매핑
	@Column(length = 100, nullable = false)
	private String rmName;
	@Column(nullable = false)
	private BigDecimal rmPrice;
	private String rmImageUrl;
	@Column(length = 100)
	private String rmDescription;
	@ColumnDefault("true")
	private Boolean isPublic;
	private LocalDateTime deletedAt;
	private UUID deletedBy;
}