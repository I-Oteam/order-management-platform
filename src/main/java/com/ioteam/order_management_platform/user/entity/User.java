package com.ioteam.order_management_platform.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ioteam.order_management_platform.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId;

	@Column(nullable = false, unique = true)
	private String nickname;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	private LocalDateTime deletedAt;
	private UUID deletedBy;

	public User(String nickname, String username, String password, String email, UserRoleEnum role) {
		this.nickname = nickname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = UUID.randomUUID();
	}
}