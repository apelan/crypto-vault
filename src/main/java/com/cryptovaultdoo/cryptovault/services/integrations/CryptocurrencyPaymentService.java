package com.cryptovaultdoo.cryptovault.services.integrations;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.services.UserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

import jakarta.validation.ValidationException;

@Component
public abstract class CryptocurrencyPaymentService {

    protected final UserDetailsService userService;
    protected final UserCryptocurrencyRepository userCryptocurrencyRepository;

    private final Logger log = LoggerFactory.getLogger(CryptocurrencyPaymentService.class);

    protected CryptocurrencyPaymentService(UserDetailsService userService,
                                           UserCryptocurrencyRepository userCryptocurrencyRepository) {
        this.userService = userService;
        this.userCryptocurrencyRepository = userCryptocurrencyRepository;
    }

    abstract void processPayment();

    abstract void paymentSucceeded(CryptocurrencyPaymentDto payment);

    abstract void paymentFailed();

    public void pay(CryptocurrencyPaymentDto payment) {
        log.info("Executing payment process...");

        if (validatePayment(payment)) {
            try {
                processPayment();
            } catch (Exception exception) {
                log.error("Payment failed");
                paymentFailed();
            }

            paymentSucceeded(payment);

            // update database to lower amount
            User user = userService.currentUser();
            Optional<UserCryptocurrency> optUserCryptocurrency = userCryptocurrencyRepository
                    .findByUserIdAndCryptocurrencyCode(user.getId(), payment.code());
            UserCryptocurrency userCryptocurrency = optUserCryptocurrency.get();
            userCryptocurrency.setAmount(userCryptocurrency.getAmount().subtract(payment.amount()));
            userCryptocurrencyRepository.save(userCryptocurrency);
        } else {
            String message = "Payment is not valid";
            log.error(message);
            throw new ValidationException(message);
        }
    }

    private boolean validatePayment(CryptocurrencyPaymentDto payment) {
        User user = userService.currentUser();
        Optional<UserCryptocurrency> optUserCryptocurrency = userCryptocurrencyRepository
                .findByUserIdAndCryptocurrencyCode(user.getId(), payment.code());

        if (optUserCryptocurrency.isEmpty()) {
            return false;
        }

        UserCryptocurrency userCryptocurrency = optUserCryptocurrency.get();

        // check amount not bigger than current balance
        if (payment.amount().compareTo(userCryptocurrency.getAmount()) > 0) {
            return false;
        }

        // we cannot pay if that leaves our balance negative
        if (userCryptocurrency.getAmount().subtract(payment.amount())
                .compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return true;
    }

}
