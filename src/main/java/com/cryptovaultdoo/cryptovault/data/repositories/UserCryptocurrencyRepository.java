package com.cryptovaultdoo.cryptovault.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCryptocurrencyRepository extends JpaRepository<UserCryptocurrency, Integer> {

    Optional<UserCryptocurrency> findByUserIdAndCryptocurrencyId(Integer userId, Integer cryptocurrencyId);
    Optional<UserCryptocurrency> findByUserIdAndCryptocurrencyCode(Integer userId, String cryptocurrencyCode);

}
