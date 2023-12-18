package com.cryptovaultdoo.cryptovault.integration.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.Cryptocurrency;
import com.cryptovaultdoo.cryptovault.data.repositories.CryptocurrencyRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
public class CryptocurrencyRepositoryTest extends GenericCrudTest<CryptocurrencyRepository, Cryptocurrency> {

    @Autowired
    private CryptocurrencyRepository repository;

    @Test
    public void findByCode_existingCryptocurrency() {
        // BTC is added with database migration, it must be present
        Optional<Cryptocurrency> cryptocurrency = repository.findByCode("BTC");

        assertTrue(cryptocurrency.isPresent());
        assertEquals("Bitcoin", cryptocurrency.get().getName());
    }

    @Test
    public void findByCode_notFound() {
        Optional<Cryptocurrency> cryptocurrency = repository.findByCode("TST_NF");

        assertFalse(cryptocurrency.isPresent());
    }

    @Override
    CryptocurrencyRepository provideRepository() {
        return repository;
    }

    @Override
    Cryptocurrency createObjectToPersist() {
        return new Cryptocurrency("crud_test", "CRUD");
    }

    @Override
    Integer provideObjectId() {
        return 6;
    }
}
