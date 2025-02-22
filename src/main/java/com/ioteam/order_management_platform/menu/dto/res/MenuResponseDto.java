package com.ioteam.order_management_platform.menu.dto.res;

import java.math.BigDecimal;
import java.util.UUID;

import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.entity.MenuStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuResponseDto {
	private UUID rmId;
	private String rmName;
	private BigDecimal rmPrice;
	private String rmImageUrl;
	private String rmDescription;
	private MenuStatus rmStatus;
	private Boolean isPublic;

	public static MenuResponseDto fromEntity(Menu menu) {
		return MenuResponseDto
			.builder()
			.rmId(menu.getRmId())
			.rmName(menu.getRmName())
			.rmPrice(menu.getRmPrice())
			.rmImageUrl(menu.getRmImageUrl())
			.rmDescription(menu.getRmDescription())
			.rmStatus(menu.getRmStatus())
			.isPublic(menu.getIsPublic())
			.build();
	}
}