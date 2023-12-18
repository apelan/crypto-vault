package com.cryptovaultdoo.cryptovault.data.repositories;

import com.cryptovaultdoo.cryptovault.data.entities.SmartContract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmartContractRepository extends JpaRepository<SmartContract, Integer> {

    Optional<SmartContract> findByContractAddress(String contractAddress);

}
