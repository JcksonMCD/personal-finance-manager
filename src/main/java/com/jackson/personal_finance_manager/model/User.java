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

    @Column(name = "Name")
    @NotNull
    private String name;

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

    // Constructors
    public User() {
    }

    public User(LocalDateTime updatedAt, LocalDateTime createdAt, String passwordHash, String email, String name, String username) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.passwordHash = passwordHash;
        this.email = email;
        this.name = name;
        this.username = username;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public @NotNull @Size(max = 50) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @Size(max = 50) String username) {
        this.username = username;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull @Email @Size(max = 100) String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email @Size(max = 100) String email) {
        this.email = email;
    }

    public @NotNull String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@NotNull String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
