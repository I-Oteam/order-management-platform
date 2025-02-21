package com.ioteam.order_management_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OrderManagementPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementPlatformApplication.class, args);
	}

}
