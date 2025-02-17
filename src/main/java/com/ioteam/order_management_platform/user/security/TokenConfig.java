package com.ioteam.order_management_platform.user.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TokenConfig {
    private final String adminToken;
    private final String ownerToken;

    public TokenConfig(@Value("${jwt.secret.admin}") String adminToken,
                       @Value("${jwt.secret.owner}") String ownerToken) {
        this.adminToken = adminToken;
        this.ownerToken = ownerToken;
    }

}
