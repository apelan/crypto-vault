package com.cryptovaultdoo.cryptovault.api.dto;

import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCryptocurrencyDto(
        @NotNull @Size(max = 50) String name,
        @NotNull @Size(max = 10) String code,
        @NotNull BigDecimal amount) {

    public static UserCryptocurrencyDto fromEntity(UserCryptocurrency userCryptocurrency) {
        return new UserCryptocurrencyDto(
                userCryptocurrency.getCryptocurrency().getName(),
                userCryptocurrency.getCryptocurrency().getCode(),
                userCryptocurrency.getAmount()
        );
    }

    public UserCryptocurrency toEntity() {
        return new UserCryptocurrency(this.name, this.code, this.amount);
    }

}
