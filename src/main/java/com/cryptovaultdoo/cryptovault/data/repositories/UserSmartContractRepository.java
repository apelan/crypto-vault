package com.cryptovaultdoo.cryptovault.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.UserSmartContract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSmartContractRepository extends JpaRepository<UserSmartContract, Integer> {
}
