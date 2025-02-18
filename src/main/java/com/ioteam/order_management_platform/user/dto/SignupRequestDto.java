package com.ioteam.order_management_platform.user.dto;

import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SignupRequestDto {
	@NotBlank(message = "nickname은 공백일 수 없습니다.")
	private String nickname;
	@NotBlank(message = "username은 공백일 수 없습니다.")
	private String username;
	@NotBlank(message = "password는 공백일 수 없습니다.")
	private String password;
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	@NotBlank(message = "email은 공백일 수 없습니다.")
	private String email;
	private boolean admin = false;
	private boolean owner = false;
	private String adminToken = "";
	private String ownerToken = "";

	public User toEntity(String password, UserRoleEnum role) {
		return User
			.builder()
			.nickname(this.nickname)
			.username(this.username)
			.password(password)
			.role(role)
			.email(this.email)
			.build();
	}
}