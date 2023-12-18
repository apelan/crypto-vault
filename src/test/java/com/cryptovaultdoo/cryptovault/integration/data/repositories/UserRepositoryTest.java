package com.cryptovaultdoo.cryptovault.integration.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
public class UserRepositoryTest extends GenericCrudTest<UserRepository, User>{

    @Autowired
    private UserRepository repository;

    @Test
    public void findByUsername_userFound() {
        Optional<User> optUser = repository.findByUsername("user");

        assertTrue(optUser.isPresent());
        assertEquals("user", optUser.get().getUsername());
        assertTrue(new BCryptPasswordEncoder()
                .matches("user", optUser.get().getPassword()));
    }

    @Test
    public void findByUsername_notFound() {
        Optional<User> optUser = repository.findByUsername("test_user_non_existing");

        assertFalse(optUser.isPresent());
    }

    @Override
    UserRepository provideRepository() {
        return repository;
    }

    @Override
    User createObjectToPersist() {
        User user = new User();
        user.setUsername("test_user");
        user.setPassword("test_user");
        return user;
    }

    @Override
    Integer provideObjectId() {
        return 2;
    }
}

