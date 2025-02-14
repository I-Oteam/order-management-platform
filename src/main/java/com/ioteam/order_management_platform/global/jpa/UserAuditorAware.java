package com.ioteam.order_management_platform.global.jpa;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

public class UserAuditorAware implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        if(null == authentication || !authentication.isAuthenticated()) {
//            return null;
//        }
//
//        //사용자 환경에 맞게 로그인한 사용자의 정보를 불러온다.
//        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
//
//        return Optional.of(userDetails.getId());

        return Optional.of(UUID.randomUUID());
    }

}