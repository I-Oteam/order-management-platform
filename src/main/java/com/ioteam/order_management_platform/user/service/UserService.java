package com.ioteam.order_management_platform.user.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.user.dto.AdminUserResponseDto;
import com.ioteam.order_management_platform.user.dto.LoginRequestDto;
import com.ioteam.order_management_platform.user.dto.SignupRequestDto;
import com.ioteam.order_management_platform.user.dto.UserInfoResponseDto;
import com.ioteam.order_management_platform.user.dto.UserSearchCondition;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.exception.UserException;
import com.ioteam.order_management_platform.user.jwt.JwtUtil;
import com.ioteam.order_management_platform.user.repository.UserRepository;
import com.ioteam.order_management_platform.user.security.TokenConfig;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenConfig tokenConfig;
	private final JwtUtil jwtUtil;

	public void signup(SignupRequestDto requestDto) {
		String username = requestDto.getUsername();
		String password = passwordEncoder.encode(requestDto.getPassword());
		String nickname = requestDto.getNickname();
		// 닉네임 중복 확인
		Optional<User> checkNickname = userRepository.findByNickname(nickname);
		if (checkNickname.isPresent()) {
			throw new CustomApiException(UserException.DUPLICATE_NICKNAME);
		}

		// 회원 중복 확인
		Optional<User> checkUsername = userRepository.findByUsername(username);
		if (checkUsername.isPresent()) {
			throw new CustomApiException(UserException.DUPLICATE_USER);
		}

		// email 중복 확인
		String email = requestDto.getEmail();
		Optional<User> checkEmail = userRepository.findByEmail(email);
		if (checkEmail.isPresent()) {
			throw new CustomApiException(UserException.DUPLICATE_EMAIL);
		}

		UserRoleEnum role = getUserRoleEnum(requestDto);

		// 사용자 등록
		User user = new User(nickname, username, password, email, role);
		userRepository.save(user);
	}

	private UserRoleEnum getUserRoleEnum(SignupRequestDto requestDto) {
		UserRoleEnum role = UserRoleEnum.CUSTOMER;
		if (requestDto.isAdmin()) {
			if (!tokenConfig.getAdminToken().equals(requestDto.getAdminToken())) {
				throw new CustomApiException(UserException.INVALID_ADMIN_TOKEN);
			}
			role = UserRoleEnum.MANAGER;
		}
		if (requestDto.isOwner()) {
			if (!tokenConfig.getOwnerToken().equals(requestDto.getOwnerToken())) {
				throw new CustomApiException(UserException.INVALID_OWNER_TOKEN);
			}
			role = UserRoleEnum.OWNER;
		}
		return role;
	}

	public String login(LoginRequestDto requestDto) { // ✅ JWT 토큰 반환하도록 변경
		String username = requestDto.getUsername();
		String password = requestDto.getPassword();

		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new CustomApiException(UserException.INVALID_USERNAME));
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new CustomApiException(UserException.INVALID_PASSWORD);
		}
		return jwtUtil.createToken(username, user.getRole());
	}

	public UserInfoResponseDto getUserByUserId(UserDetailsImpl userDetails, UUID userId) {
		if (!userDetails.getUserId().equals(userId)) {
			throw new CustomApiException(UserException.UNAUTHORIZED_ACCESS);
		}
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new CustomApiException(UserException.USER_NOT_FOUND));
		return UserInfoResponseDto.from(user);
	}

	public CommonPageResponse<AdminUserResponseDto> searchUsersByCondition(UserDetailsImpl userDetails,
		UserSearchCondition condition,
		Pageable pageable) {
		UserRoleEnum role = userDetails.getUser().getRole();
		if (role != UserRoleEnum.MASTER && role != UserRoleEnum.MANAGER) {
			throw new CustomApiException(UserException.NO_PERMISSION);
		}
		Page<AdminUserResponseDto> userDtoList = userRepository.searchUserByCondition(condition, pageable)
			.map(AdminUserResponseDto::from);
		return new CommonPageResponse<>(userDtoList);
	}
}