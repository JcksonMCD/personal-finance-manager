package com.jackson.personal_finance_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;

    @Column(name = "Username", unique = true, nullable = false)
    @NotNull
    @Size(max = 50)
    private String username;

    @Column(name = "Email", unique = true, nullable = false)
    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    @Column(name = "PasswordHash", nullable = false)
    @NotNull
    private String passwordHash;

    @Column(name = "CreatedAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
