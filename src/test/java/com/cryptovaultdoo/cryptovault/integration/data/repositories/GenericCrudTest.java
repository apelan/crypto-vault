package com.cryptovaultdoo.cryptovault.integration.data.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class GenericCrudTest<R extends JpaRepository<T, Integer>, T> {

    abstract R provideRepository();
    abstract T createObjectToPersist();
    abstract Integer provideObjectId();

    @Test
    public void test_crud_operations() {
        R repository = provideRepository();
        // SAVE
        T newObject = createObjectToPersist();
        repository.save(newObject);

        // READ
        Optional<T> optPersistedObject = repository.findById(provideObjectId());
        assertTrue(optPersistedObject.isPresent());

        T persistedObject = optPersistedObject.get();

        // DELETE
        repository.delete(persistedObject);
        Optional<T> deletedObject = repository.findById(provideObjectId());
        assertFalse(deletedObject.isPresent());

    }

}
