package com.vimal.tournax.controller;

import com.vimal.tournax.dto.AuthResponse;
import com.vimal.tournax.entity.User;
import com.vimal.tournax.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * Controller for handling user registration and login.
 */
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        user.setId(null);
        User registered = authService.registerUser(user);
        registered.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        
        return ResponseEntity.ok(new AuthResponse("Login successful", username, role));
    }
}
