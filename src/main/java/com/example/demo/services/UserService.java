package com.example.demo.services;

import com.example.demo.domain.model.Role;
import com.example.demo.domain.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository;

    /**
     * Saves user
     *
     * @param user user
     * @return saved user
     * @throws IllegalArgumentException if user already exists
     */
    public User save(User user) throws IllegalArgumentException {
        if (repository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User with this username already exists");
        }
        return repository.save(user);
    }

    /**
     * Get user by username
     *
     * @param username username
     * @return user with this username
     * @throws RuntimeException if user not found
     */
    public User getUserByUserName(String username) throws RuntimeException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with this username not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUserName;
    }

    /**
     * Get current user from Spring Security context
     *
     * @return current user
     */
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUserName(username);
    }

    /**
     * Get admin rights to current user
     */
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

}
