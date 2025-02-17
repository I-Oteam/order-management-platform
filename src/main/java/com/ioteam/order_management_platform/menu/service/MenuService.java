package com.ioteam.order_management_platform.menu.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.menu.dto.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.MenuResponseDto;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

	private final MenuRepository menuRepository;

	@Transactional
	public MenuResponseDto createMenu(CreateMenuRequestDto requestDto) {
		UUID resId = requestDto.getResId();
		// TODO: restaurant entity 생성 후 실제 존재하는지 확인
		Menu menu = requestDto.toEntity(requestDto);
		Menu savedMenu = menuRepository.save(menu);
		return MenuResponseDto.fromEntity(savedMenu);
	}
}
