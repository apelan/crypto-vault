package com.cryptovaultdoo.cryptovault.services.integrations;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.services.UserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public non-sealed class CoinGateService extends CryptocurrencyPaymentService {

    Logger log = LoggerFactory.getLogger(CoinGateService.class);

    public CoinGateService(UserDetailsService userService,
                           UserCryptocurrencyRepository userCryptocurrencyRepository) {
        super(userService, userCryptocurrencyRepository);
    }

    @Override
    protected void processPayment() {
        log.info("[SIMULATION] CoinGate payment processing...");
    }

    @Override
    void paymentSucceeded(CryptocurrencyPaymentDto payment) {
        log.info("[SIMULATION] CoinGate payment succeeded!");
    }

    @Override
    void paymentFailed() {
        log.info("[SIMULATION] CoinGate payment failed!");
    }
}
