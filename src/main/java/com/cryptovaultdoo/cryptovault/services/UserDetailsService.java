package com.cryptovaultdoo.cryptovault.services;

import com.cryptovaultdoo.cryptovault.data.repositories.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Used in order to load user by unique username;
     *
     * @param username the username identifying the user whose data is required.
     * @return {@link UserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.cryptovaultdoo.cryptovault.data.entities.User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist."));

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    /**
     * Method used to retrieve current user from SecurityContext.
     *
     * @return {@link com.cryptovaultdoo.cryptovault.data.entities.User}
     */
    public com.cryptovaultdoo.cryptovault.data.entities.User currentUser() {
        return userRepository
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist."));
    }

}
