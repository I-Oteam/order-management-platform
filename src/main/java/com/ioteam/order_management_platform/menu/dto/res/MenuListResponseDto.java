package com.ioteam.order_management_platform.menu.dto.res;

import java.util.List;

import com.ioteam.order_management_platform.menu.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuListResponseDto {
	private List<MenuResponseDto> menuList;
	private int totalCount;

	public static MenuListResponseDto of(List<Menu> menuList) {
		List<MenuResponseDto> responseList = menuList.stream()
			.map(menu -> MenuResponseDto.fromEntity(menu))
			.toList();
		return MenuListResponseDto.builder()
			.menuList(responseList)
			.totalCount(responseList.size())
			.build();
	}
}

