package com.ioteam.order_management_platform.menu.dto.req;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.entity.MenuStatus;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateMenuRequestDto {

	@NotNull
	private UUID resId;
	@NotNull
	@Length(min = 1, max = 100)
	private String rmName;
	@NotNull
	@Min(value = 0)
	private BigDecimal rmPrice;
	private String rmImageUrl;
	@Length(max = 100)
	private String rmDescription;
	private MenuStatus rmStatus;
	private Boolean isPublic;

	public Menu toEntity(CreateMenuRequestDto requestDto, Restaurant restaurant) {
		return Menu
			.builder()
			.restaurant(restaurant)
			.rmName(requestDto.getRmName())
			.rmPrice(requestDto.getRmPrice())
			.rmImageUrl(requestDto.getRmImageUrl())
			.rmDescription(requestDto.getRmDescription())
			.rmStatus(requestDto.getRmStatus())
			.isPublic(requestDto.getIsPublic())
			.build();
	}

}
