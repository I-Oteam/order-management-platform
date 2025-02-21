package com.ioteam.order_management_platform.user.dto.req;

import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SignupRequestDto {
	@NotBlank(message = "별칭은 공백일 수 없습니다.")
	private String nickname;

	@NotBlank(message = "아이디는 공백일 수 없습니다.")
	@Size(min = 4, max = 10, message = "아이디는 4~10자 사이여야 합니다.")
	@Pattern(regexp = "^[a-z0-9]{4,11}$", message = "아이디는 알파벳 소문자(a-z)와 숫자(0-9)만 포함해야 합니다.")
	private String username;

	@NotBlank(message = "비밀번호는 공백일 수 없습니다.")
	@Size(min = 8, max = 15, message = "비밀번호는 8~15자 사이여야 합니다.")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
		message = "비밀번호는 최소 하나의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
	)
	private String password;

	@Email(message = "올바른 이메일 형식이 아닙니다.")
	@NotBlank(message = "이메일은 공백일 수 없습니다.")
	private String email;

	@NotNull(message = "권한을 선택해주세요.")
	private UserRoleEnum role;
	private String masterToken = "";
	private String managerToken = "";
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