package com.ioteam.order_management_platform.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ioteam.order_management_platform.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByNickname(String nickname);

	Optional<User> findByUserId(UUID userId);
}