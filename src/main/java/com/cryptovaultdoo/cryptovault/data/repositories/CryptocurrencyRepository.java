package com.cryptovaultdoo.cryptovault.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.Cryptocurrency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Integer> {
    Optional<Cryptocurrency> findByCode(String code);

}
