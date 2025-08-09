/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.controller;

import com.java1kind.model.User;
import com.java1kind.service.JwtService;
import com.java1kind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid username/password.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        Optional<User> existing = userService.findByUsername(username);
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
        User newUser = userService.registerUser(username, email, password);
        return ResponseEntity.ok("User registered: " + newUser.getId());
    }
}
