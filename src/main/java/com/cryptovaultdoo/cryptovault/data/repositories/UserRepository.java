package com.cryptovaultdoo.cryptovault.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
