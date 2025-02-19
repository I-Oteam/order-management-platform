package com.ioteam.order_management_platform.user.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
	CUSTOMER(Authority.CUSTOMER),  // 사용자(고객) 권한
	OWNER(Authority.OWNER),     // 사용자(가게주인) 권한
	MANAGER(Authority.MANAGER),  // 관리자 권한
	MASTER(Authority.MASTER);

	private final String authority;

	UserRoleEnum(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public static class Authority {
		public static final String CUSTOMER = "ROLE_CUSTOMER";
		public static final String OWNER = "ROLE_OWNER";
		public static final String MANAGER = "ROLE_MANAGER";
		public static final String MASTER = "ROLE_MASTER";
	}
}
