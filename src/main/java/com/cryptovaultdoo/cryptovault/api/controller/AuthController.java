package com.cryptovaultdoo.cryptovault.api.controller;

import com.cryptovaultdoo.cryptovault.security.AuthenticationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication API", description = "Endpoints for user authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    @Operation(summary = "User login", description = "Performs user login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        return Map.of("token", authenticationService.login(username, password));
    }

}
