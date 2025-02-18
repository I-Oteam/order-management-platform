package com.ioteam.order_management_platform.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
	private String username;
	private String password;
}