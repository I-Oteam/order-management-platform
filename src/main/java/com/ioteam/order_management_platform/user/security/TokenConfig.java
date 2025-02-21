package com.ioteam.order_management_platform.user.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class TokenConfig {
	private final String masterToken;
	private final String managerToken;
	private final String ownerToken;

	public TokenConfig(@Value("${jwt.secret.master}") String masterToken,
		@Value("${jwt.secret.manager}") String managerToken,
		@Value("${jwt.secret.owner}") String ownerToken) {
		this.masterToken = masterToken;
		this.managerToken = managerToken;
		this.ownerToken = ownerToken;
	}

}