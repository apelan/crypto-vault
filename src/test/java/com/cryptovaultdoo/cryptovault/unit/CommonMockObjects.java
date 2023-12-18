package com.cryptovaultdoo.cryptovault.unit;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.data.entities.Cryptocurrency;
import com.cryptovaultdoo.cryptovault.data.entities.SmartContract;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;
import com.cryptovaultdoo.cryptovault.data.entities.UserSmartContract;

import java.math.BigDecimal;
import java.util.List;

public class CommonMockObjects {

    public static User mockUser() {
        User user = new User();
        user.setId(123);
        user.setUsername("test_user");
        user.setPassword("test_user");
        user.setSmartContracts(List.of(mockUserSmartContract(user)));
        user.setCryptocurrencies(List.of(mockUserCryptocurrency()));
        return user;
    }

    public static SmartContract mockSmartContract() {
        return new SmartContract("Test Smart Contract", "0x000000000000000000000000000000000test123");
    }

    public static UserSmartContract mockUserSmartContract(User user) {
        return new UserSmartContract(user, mockSmartContract());
    }

    public static UserCryptocurrency mockUserCryptocurrency() {
        return new UserCryptocurrency("Test currency", "TST", BigDecimal.valueOf(100.00));
    }

    public static Cryptocurrency mockCryptocurrency() {
        return new Cryptocurrency("Test currency", "TST");
    }

    public static CryptocurrencyPaymentDto mockCryptocurrencyPaymentDto() {
        return new CryptocurrencyPaymentDto("TST", BigDecimal.valueOf(0.10));
    }

}
