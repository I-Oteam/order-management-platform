package com.ioteam.order_management_platform.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {

		Info info = new Info()
			.title("Order Management Platform API")
			.description("주문 관리 시스템을 제공하는 API docs")
			.version("1.0.0");

		// Security 스키마 설정
		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER)
			.name(HttpHeaders.AUTHORIZATION);

		// Security 요청 설정
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("JWT");

		return new OpenAPI()
			// Security 인증 컴포넌트 설정
			.components(new Components().addSecuritySchemes("JWT", securityScheme))
			// API 마다 Security 인증 컴포넌트 설정
			.addSecurityItem(securityRequirement)
			.info(info);
	}
}