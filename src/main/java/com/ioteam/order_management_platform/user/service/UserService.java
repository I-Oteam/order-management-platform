package com.ioteam.order_management_platform.user.service;

import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.user.dto.SignupRequestDto;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.entity.UserRoleEnum;
import com.ioteam.order_management_platform.user.exception.UserException;
import com.ioteam.order_management_platform.user.repository.UserRepository;
import com.ioteam.order_management_platform.user.security.TokenConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfig tokenConfig;

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
            if (!tokenConfig.getAdminToken().equals(requestDto.getOwnerToken())){
                throw new CustomApiException(UserException.INVALID_OWNER_TOKEN);
            }
            role = UserRoleEnum.OWNER;
        }
        return role;
    }
}