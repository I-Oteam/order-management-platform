package com.ioteam.order_management_platform.ai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.ai.entity.AIResponse;

public interface AiRepository extends JpaRepository<AIResponse, UUID> {
}
