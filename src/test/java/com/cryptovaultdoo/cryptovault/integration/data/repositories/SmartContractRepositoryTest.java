package com.cryptovaultdoo.cryptovault.integration.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.SmartContract;
import com.cryptovaultdoo.cryptovault.data.repositories.SmartContractRepository;

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
public class SmartContractRepositoryTest extends GenericCrudTest<SmartContractRepository, SmartContract>{

    @Autowired
    private SmartContractRepository repository;

    @Test
    public void findByContractAddress_existingContract() {
        // BTC is added with database migration, it must be present
        Optional<SmartContract> contract = repository
                .findByContractAddress("0x00000000000000000000000000000000001a2b3c");

        assertTrue(contract.isPresent());
        assertEquals("My Smart Contract", contract.get().getName());
    }

    @Test
    public void findByContractAddress_notFound() {
        Optional<SmartContract> contract = repository.findByContractAddress("TST_NF");

        assertFalse(contract.isPresent());
    }

    @Override
    SmartContractRepository provideRepository() {
        return repository;
    }

    @Override
    SmartContract createObjectToPersist() {
        return new SmartContract("Test smart contract", "testhash123");
    }

    @Override
    Integer provideObjectId() {
        return 2;
    }
}
