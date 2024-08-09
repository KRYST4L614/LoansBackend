package com.example.demo.controllers;

import com.example.demo.domain.dto.LoginRequest;
import com.example.demo.domain.dto.RegistrationResponse;
import com.example.demo.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Authorize user")
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest request) {
        return authenticationService.login(request);
    }

    @Operation(summary = "Registration user")
    @PostMapping("/registration")
    public RegistrationResponse registration(@RequestBody @Valid LoginRequest request) {
        return authenticationService.registration(request);
    }
}
