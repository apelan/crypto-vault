package com.cryptovaultdoo.cryptovault.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;
    private final JWTManager jwtManager;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTManager jwtManager) {
        this.authenticationManager = authenticationManager;
        this.jwtManager = jwtManager;
    }

    /**
     * Used to login user into the app, returning JWT.
     *
     * @param username String
     * @param password String
     * @return JWT
     */
    public String login(String username, String password) {
        log.info("Attempting login for user {}", username);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtManager.generateToken(authentication);
        log.info("User {} logged in ", username);
        return token;
    }

}
