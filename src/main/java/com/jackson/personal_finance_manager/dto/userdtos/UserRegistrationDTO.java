package com.jackson.personal_finance_manager.dto.userdtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class UserRegistrationDTO {

    @Schema(description = "The username of the user", example = "john_doe")
    @NotBlank(message = "Username is mandatory")
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @Schema(description = "The name of the user", example = "John")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Schema(description = "The email of the user", example = "john.doe@example.com")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Schema(description = "The password of the user", example = "password123")
    @NotBlank(message = "Password is mandatory")
    private String password;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotBlank(message = "Name is mandatory") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is mandatory") String name) {
        this.name = name;
    }
}
