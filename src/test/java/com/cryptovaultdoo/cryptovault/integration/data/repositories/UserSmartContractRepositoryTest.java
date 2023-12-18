package com.cryptovaultdoo.cryptovault.integration.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.SmartContract;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserSmartContract;
import com.cryptovaultdoo.cryptovault.data.repositories.UserSmartContractRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
public class UserSmartContractRepositoryTest extends GenericCrudTest<UserSmartContractRepository, UserSmartContract>{

    @Autowired
    private UserSmartContractRepository repository;


    @Override
    UserSmartContractRepository provideRepository() {
        return repository;
    }

    @Override
    UserSmartContract createObjectToPersist() {
        User user = new User();
        user.setId(1);
        user.setUsername("test_user");
        user.setPassword("test_user");

        SmartContract smartContract = new SmartContract("Test", "testhash123");

        return new UserSmartContract(user, smartContract);
    }

    @Override
    Integer provideObjectId() {
        return 1;
    }
}
