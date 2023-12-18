package com.cryptovaultdoo.cryptovault.api.dto;

import com.cryptovaultdoo.cryptovault.data.entities.SmartContract;
import com.cryptovaultdoo.cryptovault.data.entities.UserSmartContract;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SmartContractDto(
        @NotNull @Size(max = 50) String name,
        @NotNull @Size(max = 255) String contractAddress) {

    public static SmartContractDto fromEntity(UserSmartContract userSmartContract) {
        return new SmartContractDto(
                userSmartContract.getSmartContract().getName(),
                userSmartContract.getSmartContract().getContractAddress()
        );
    }

    public SmartContract toEntity() {
        return new SmartContract(this.name, this.contractAddress);
    }

}
