package com.ioteam.order_management_platform.menu.controller;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.menu.dto.CreateMenuRequestDto;
import com.ioteam.order_management_platform.menu.dto.MenuResponseDto;
import com.ioteam.order_management_platform.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "상품 등록")
    @PostMapping()
    public ResponseEntity<CommonResponse<MenuResponseDto>> createMenu(@RequestBody CreateMenuRequestDto requestDto){
        MenuResponseDto responseDto = menuService.createMenu(requestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/menus/" + responseDto.getRmId().toString())
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .body(new CommonResponse<MenuResponseDto>("메뉴가 성공적으로 등록되었습니다.", responseDto));
    }

}
