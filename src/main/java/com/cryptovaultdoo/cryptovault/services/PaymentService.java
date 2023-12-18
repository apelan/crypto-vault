package com.cryptovaultdoo.cryptovault.services;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.api.enumerator.PaymentType;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.services.integrations.CoinGateService;
import com.cryptovaultdoo.cryptovault.services.integrations.BinancePaymentService;
import com.cryptovaultdoo.cryptovault.services.integrations.CryptocurrencyPaymentService;
import com.cryptovaultdoo.cryptovault.services.integrations.Some3rdPartyPaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final UserDetailsService userService;
    private final UserCryptocurrencyRepository userCryptocurrencyRepository;

    public PaymentService(UserDetailsService userService, UserCryptocurrencyRepository userCryptocurrencyRepository) {
        this.userService = userService;
        this.userCryptocurrencyRepository = userCryptocurrencyRepository;
    }

    /**
     * Method used to execute payment with selected payment type.
     *
     * @param paymentType {@link PaymentType}
     * @param payment {@link CryptocurrencyPaymentDto}
     */
    public void payWithCryptocurrency(PaymentType paymentType, CryptocurrencyPaymentDto payment) {
        log.info("Initialize payment {}{} with {}", payment.amount(), payment.code(), paymentType);
        CryptocurrencyPaymentService paymentService = switch (paymentType) {
            case BINANCE -> new BinancePaymentService(userService, userCryptocurrencyRepository);
            case COIN_GATE -> new CoinGateService(userService, userCryptocurrencyRepository);
            case SOME_3RD_PARTY -> new Some3rdPartyPaymentService(userService, userCryptocurrencyRepository);
        };

        paymentService.pay(payment);
    }

}
