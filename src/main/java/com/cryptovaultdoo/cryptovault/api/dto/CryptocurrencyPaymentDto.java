package com.cryptovaultdoo.cryptovault.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record CryptocurrencyPaymentDto(@NotNull String code, @NotNull BigDecimal amount) {
}
