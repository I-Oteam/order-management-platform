package com.ioteam.order_management_platform.user.dto;

import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    String username;
    UserRoleEnum role;
}