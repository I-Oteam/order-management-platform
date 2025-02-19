package com.ioteam.order_management_platform.user.dto.req;

import lombok.Getter;

@Getter
public class LoginRequestDto {
	private String username;
	private String password;
}