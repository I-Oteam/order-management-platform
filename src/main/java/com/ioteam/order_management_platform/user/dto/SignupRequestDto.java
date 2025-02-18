package com.ioteam.order_management_platform.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}