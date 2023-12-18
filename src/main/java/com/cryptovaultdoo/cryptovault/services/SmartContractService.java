package com.cryptovaultdoo.cryptovault.services;

import com.cryptovaultdoo.cryptovault.api.dto.SmartContractDto;
import com.cryptovaultdoo.cryptovault.data.entities.SmartContract;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserSmartContract;
import com.cryptovaultdoo.cryptovault.data.repositories.SmartContractRepository;
import com.cryptovaultdoo.cryptovault.data.repositories.UserSmartContractRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class SmartContractService {

    Logger log = LoggerFactory.getLogger(SmartContractService.class);

    private final UserDetailsService userService;
    private final SmartContractRepository smartContractRepository;
    private final UserSmartContractRepository userSmartContractRepository;

    public SmartContractService(UserDetailsService userService, SmartContractRepository smartContractRepository, UserSmartContractRepository userSmartContractRepository) {
        this.userService = userService;
        this.smartContractRepository = smartContractRepository;
        this.userSmartContractRepository = userSmartContractRepository;
    }

    /**
     * Returns all Smart Contracts for current user.
     *
     * @return List of {@link SmartContractDto}
     */
    public List<SmartContractDto> inspectContracts() {
        log.info("Fetching smart contracts...");
        User user = userService.currentUser();

        return user.getSmartContracts().stream()
                .map(SmartContractDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Used to deposit new Smart Contract for current user.
     *
     * @param dto {@link SmartContractDto}
     */
    @Transactional
    public void depositContract(SmartContractDto dto) {
        log.info("Deposit Smart Contract with address {}", dto.contractAddress());

        User user = userService.currentUser();

        // Not that much familiar with domain, I believe we should validate that contract address is unique
        smartContractRepository.findByContractAddress(dto.contractAddress())
                .ifPresent(contract -> {
                    String message = String.format("Contract with address %s already exists", dto.contractAddress());
                    log.error(message);
                    throw new ValidationException(message);
                });

        SmartContract smartContract = dto.toEntity();
        UserSmartContract userSmartContract = new UserSmartContract(user, smartContract);
        userSmartContractRepository.save(userSmartContract);
    }


}
