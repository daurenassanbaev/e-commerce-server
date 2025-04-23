package com.ecommerce.userservice.user.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keycloak_id", nullable = false, unique = true)
    private UUID keycloakId;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;
    private String lastName;

    private String phoneNumber;

    private boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
