package com.cryptovaultdoo.cryptovault.services.integrations;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.services.UserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public non-sealed class BinancePaymentService extends CryptocurrencyPaymentService {

    Logger log = LoggerFactory.getLogger(BinancePaymentService.class);

    public BinancePaymentService(UserDetailsService userService,
                                 UserCryptocurrencyRepository userCryptocurrencyRepository) {
        super(userService, userCryptocurrencyRepository);
    }

    @Override
    protected void processPayment() {
        log.info("[SIMULATION] Binance payment processing...");
        // here could be some logic for processing payment, this is just simulation
    }

    @Override
    void paymentSucceeded(CryptocurrencyPaymentDto payment) {
        log.info("[SIMULATION] Binance payment succeeded!");
        // here could be created receipt, sent email etc..
    }

    @Override
    void paymentFailed() {
        log.error("[SIMULATION] Binance payment failed!");
        // audit log, rollback transaction, notify user that payment failed etc
    }
}
