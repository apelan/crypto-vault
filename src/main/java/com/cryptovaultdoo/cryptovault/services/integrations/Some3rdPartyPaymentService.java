package com.cryptovaultdoo.cryptovault.services.integrations;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.services.UserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public non-sealed class Some3rdPartyPaymentService extends CryptocurrencyPaymentService {

    Logger log = LoggerFactory.getLogger(Some3rdPartyPaymentService.class);

    public Some3rdPartyPaymentService(UserDetailsService userService,
                                      UserCryptocurrencyRepository userCryptocurrencyRepository) {
        super(userService, userCryptocurrencyRepository);
    }

    @Override
    protected void processPayment() {
        log.info("[SIMULATION] 3rd party payment processing...");
    }

    @Override
    void paymentSucceeded(CryptocurrencyPaymentDto payment) {
        log.info("[SIMULATION] 3rd party payment succeeded!");
    }

    @Override
    void paymentFailed() {
        log.error("[SIMULATION] 3rd party payment failed!");
    }
}
