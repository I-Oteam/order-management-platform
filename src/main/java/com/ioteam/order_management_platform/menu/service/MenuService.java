package com.ioteam.order_management_platform.menu.service;

import com.ioteam.order_management_platform.menu.dto.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.MenuResponseDto;
import com.ioteam.order_management_platform.menu.entity.Menu;
import com.ioteam.order_management_platform.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public MenuResponseDto createMenu(CreateMenuRequestDto requestDto) {
        Menu menu = new Menu(requestDto);
        Menu savedMenu = menuRepository.save(menu);
        return savedMenu.toResponseDto();

    }
}
