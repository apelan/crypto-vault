package com.cryptovaultdoo.cryptovault.integration.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.Cryptocurrency;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
public class UserCryptocurrencyRepositoryTest extends GenericCrudTest<UserCryptocurrencyRepository, UserCryptocurrency>{

    @Autowired
    private UserCryptocurrencyRepository repository;

    @Test
    public void findByUserIdAndCryptocurrencyId_existingRelation() {
        // BTC is added with database migration, it must be present
        Optional<UserCryptocurrency> relation = repository
                .findByUserIdAndCryptocurrencyId(1, 2);

        assertTrue(relation.isPresent());
        assertEquals("Ethereum", relation.get().getCryptocurrency().getName());
        assertEquals("ETH", relation.get().getCryptocurrency().getCode());
        assertEquals(BigDecimal.valueOf(3.22), relation.get().getAmount());
    }

    @Test
    public void findByUserIdAndCryptocurrencyId_notFound() {
        Optional<UserCryptocurrency> relation = repository.findByUserIdAndCryptocurrencyId(1, 50);

        assertFalse(relation.isPresent());
    }

    @Override
    UserCryptocurrencyRepository provideRepository() {
        return repository;
    }

    @Override
    UserCryptocurrency createObjectToPersist() {
        User user = new User();
        user.setId(1);
        user.setUsername("test_user");
        user.setPassword("test_user");

        Cryptocurrency cryptocurrency = new Cryptocurrency("crud_test_relation", "CRUD_RLT");

        UserCryptocurrency userCryptocurrency = new UserCryptocurrency(user, cryptocurrency);
        userCryptocurrency.setAmount(BigDecimal.valueOf(200.00));
        return userCryptocurrency;
    }

    @Override
    Integer provideObjectId() {
        return 4;
    }
}
