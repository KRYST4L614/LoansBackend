package com.example.demo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Registration request")
public class LoginRequest {
    @Schema(description = "Username", example = "John")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", example = "qwerty123")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
