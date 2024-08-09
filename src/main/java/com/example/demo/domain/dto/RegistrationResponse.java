package com.example.demo.domain.dto;

import com.example.demo.domain.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Logged user")
public class RegistrationResponse {
    @Schema(description = "Name of user")
    private String name;

    @Schema(description = "Role of user")
    private Role role;
}
