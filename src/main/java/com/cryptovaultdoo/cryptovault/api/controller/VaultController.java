package com.cryptovaultdoo.cryptovault.api.controller;

import com.cryptovaultdoo.cryptovault.api.dto.SmartContractDto;
import com.cryptovaultdoo.cryptovault.api.dto.UserCryptocurrencyDto;
import com.cryptovaultdoo.cryptovault.services.CryptocurrencyService;
import com.cryptovaultdoo.cryptovault.services.SmartContractService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/vault")
@Tag(name = "Vault API", description = "Endpoints used to manipulate with Vault")
public class VaultController {

    private final SmartContractService smartContractService;
    private final CryptocurrencyService cryptocurrencyService;

    public VaultController(SmartContractService smartContractService, CryptocurrencyService cryptocurrencyService) {
        this.smartContractService = smartContractService;
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @GetMapping("/contracts")
    @Operation(
            summary = "Inspect Smart Contracts",
            description = "Endpoint used to fetch Smart Contracts for current user"
    )
    public List<SmartContractDto> inspectContracts() {
        return smartContractService.inspectContracts();
    }

    @PostMapping(value = "/contracts", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Deposit Smart Contract",
            description = "Endpoint used to deposit Smart Contracts for current user"
    )
    public void depositContract(@Valid @RequestBody SmartContractDto smartContractDto) {
        smartContractService.depositContract(smartContractDto);
    }

    @GetMapping("/cryptocurrencies")
    @Operation(
            summary = "Inspect Cryptocurrencies",
            description = "Endpoint used to fetch Cryptocurrencies for current user"
    )
    public List<UserCryptocurrencyDto> inspectCryptocurrencies() {
        return cryptocurrencyService.inspectCryptocurrencies();
    }

    @PostMapping(value = "/cryptocurrencies", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Deposit Cryptocurrency",
            description = "Endpoint used to deposit Cryptocurrency for current user"
    )
    public void depositorCryptocurrency(@Valid @RequestBody UserCryptocurrencyDto userCryptocurrencyDto) {
        cryptocurrencyService.depositCryptocurrency(userCryptocurrencyDto);
    }


}
