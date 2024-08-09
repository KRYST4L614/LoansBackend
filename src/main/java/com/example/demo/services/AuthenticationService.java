package com.example.demo.services;

import com.example.demo.domain.dto.LoginRequest;
import com.example.demo.domain.dto.RegistrationResponse;
import com.example.demo.domain.model.Role;
import com.example.demo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * User registration
     *
     * @param request user's data
     * @return token
     */
    public RegistrationResponse registration(LoginRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.save(user);
        return RegistrationResponse.builder().name(user.getUsername()).role(user.getRole()).build();
    }

    /**
     * User authentication
     *
     * @param request user's data
     * @return token
     */
    public String login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return jwt;
    }
}

