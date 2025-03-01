package com.ioteam.order_management_platform.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.ioteam.order_management_platform")
public class OpenFeignConfig {
}
