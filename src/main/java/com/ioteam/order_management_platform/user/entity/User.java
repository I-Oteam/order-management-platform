package com.ioteam.order_management_platform.user.entity;

import com.ioteam.order_management_platform.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_id;

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

    public User(String username, String password, String email, UserRoleEnum role) {
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