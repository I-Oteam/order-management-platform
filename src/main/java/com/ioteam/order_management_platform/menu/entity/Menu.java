package com.ioteam.order_management_platform.menu.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import com.ioteam.order_management_platform.menu.dto.req.UpdateMenuRequestDto;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@ColumnDefault("'ON_SALE'")
	@Enumerated(EnumType.STRING)
	private MenuStatus rmStatus;
	@ColumnDefault("true")
	private Boolean isPublic;

	public Menu updateMenu(UpdateMenuRequestDto requestDto) {
		if (requestDto.getRmName() != null)
			this.rmName = requestDto.getRmName();
		if (requestDto.getRmPrice() != null)
			this.rmPrice = requestDto.getRmPrice();
		if (requestDto.getRmImageUrl() != null)
			this.rmImageUrl = requestDto.getRmImageUrl();
		if (requestDto.getRmDescription() != null)
			this.rmDescription = requestDto.getRmDescription();
		if (requestDto.getRmStatus() != null)
			this.rmStatus = requestDto.getRmStatus();
		if (requestDto.getIsPublic() != null)
			this.isPublic = requestDto.getIsPublic();
		return this;
	}

	public Menu hiddenMenu() {
		this.rmStatus = MenuStatus.HIDDEN;
		return this;
	}

}