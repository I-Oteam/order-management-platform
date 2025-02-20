package com.ioteam.order_management_platform.user.service;

import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.global.dto.CommonPageResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.user.dto.req.LoginRequestDto;
import com.ioteam.order_management_platform.user.dto.req.SignupRequestDto;
import com.ioteam.order_management_platform.user.dto.req.UserSearchCondition;
import com.ioteam.order_management_platform.user.dto.res.AdminUserResponseDto;
import com.ioteam.order_management_platform.user.dto.res.UserInfoResponseDto;
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

	@Transactional
	public void signup(SignupRequestDto requestDto) {
		// 비밀번호 암호화 및 권한 부여
		String password = passwordEncoder.encode(requestDto.getPassword());
		UserRoleEnum role = getUserRoleEnum(requestDto);

		// DB 저장 시 중복 검사를 통해 저장 시점에 중복 검사로 동시성 문제 해결
		try {
			User user = requestDto.toEntity(password, role);
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new CustomApiException(UserException.DUPLICATE_FIELD);
		}
	}

	public String login(LoginRequestDto requestDto) {
		String username = requestDto.getUsername();
		String password = requestDto.getPassword();

		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new CustomApiException(UserException.INVALID_USERNAME));

		if (user.getDeletedAt() != null) {
			throw new CustomApiException(UserException.USER_DELETED);
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new CustomApiException(UserException.INVALID_PASSWORD);
		}
		return jwtUtil.createToken(username, user.getRole());
	}

	@Transactional(readOnly = true)
	public UserInfoResponseDto getUserByUserId(UserDetailsImpl userDetails, UUID userId) {
		if (!userDetails.getUserId().equals(userId)) {
			throw new CustomApiException(UserException.UNAUTHORIZED_ACCESS);
		}
		User user = userRepository.findByUserIdAndDeletedAtIsNull(userId)
			.orElseThrow(() -> new CustomApiException(UserException.USER_NOT_FOUND));
		if (user.getDeletedAt() != null) {
			throw new CustomApiException(UserException.USER_DELETED); // 삭제된 사용자
		}
		return UserInfoResponseDto.from(user);
	}

	@Transactional(readOnly = true)
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

	@Transactional
	public void softDeleteUser(UUID userId, UserDetailsImpl userDetails) {
		if (userDetails.getRole() == UserRoleEnum.CUSTOMER || userDetails.getRole() == UserRoleEnum.OWNER) {
			if (!userId.equals(userDetails.getUserId())) {
				throw new CustomApiException(UserException.UNAUTHORIZED_ACCESS); // 본인이 아닌 다른 사용자가 탈퇴 시도
			}
		}
		User user = userRepository.findByUserIdAndDeletedAtIsNull(userId)
			.orElseThrow(() -> new CustomApiException(UserException.INVALID_USER_ID));
		user.softDelete(userId);
	}

	private UserRoleEnum getUserRoleEnum(SignupRequestDto requestDto) {
		try {
			UserRoleEnum role = requestDto.getRole();
			if (role == UserRoleEnum.MASTER) {
				if (!tokenConfig.getMasterToken().equals(requestDto.getMasterToken())) {
					throw new CustomApiException(UserException.INVALID_MASTER_TOKEN);
				}
				role = UserRoleEnum.MANAGER;
			} else if (role == UserRoleEnum.MANAGER) {
				if (!tokenConfig.getManagerToken().equals(requestDto.getManagerToken())) {
					throw new CustomApiException(UserException.INVALID_MANAGER_TOKEN);
				}
			} else if (role == UserRoleEnum.OWNER) {
				if (!tokenConfig.getOwnerToken().equals(requestDto.getOwnerToken())) {
					throw new CustomApiException(UserException.INVALID_OWNER_TOKEN);
				}
			}
			return role;
		} catch (IllegalArgumentException e) {
			throw new CustomApiException(UserException.INVALID_ROLE);
		}

	}
}