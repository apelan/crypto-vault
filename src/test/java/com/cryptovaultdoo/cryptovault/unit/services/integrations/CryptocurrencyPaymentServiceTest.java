package com.cryptovaultdoo.cryptovault.unit.services.integrations;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.services.UserDetailsService;
import com.cryptovaultdoo.cryptovault.services.integrations.BinancePaymentService;
import com.cryptovaultdoo.cryptovault.services.integrations.CryptocurrencyPaymentService;
import com.cryptovaultdoo.cryptovault.unit.CommonMockObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;


import jakarta.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CryptocurrencyPaymentServiceTest {

    @Mock
    private UserDetailsService userService;

    @Mock
    private UserCryptocurrencyRepository userCryptocurrencyRepository;

    private CryptocurrencyPaymentService service;

    @BeforeEach
    public void setup() {
        service = new BinancePaymentService(userService, userCryptocurrencyRepository);
    }

    @Test
    public void pay_paymentSucceeded() {
        // GIVEN
        CryptocurrencyPaymentDto payment = CommonMockObjects.mockCryptocurrencyPaymentDto();
        User user = CommonMockObjects.mockUser();
        UserCryptocurrency userCryptocurrency = CommonMockObjects.mockUserCryptocurrency();
        when(userService.currentUser()).thenReturn(user);
        when(userCryptocurrencyRepository.findByUserIdAndCryptocurrencyCode(user.getId(), payment.code()))
                .thenReturn(Optional.of(userCryptocurrency));

        // WHEN
        service.pay(payment);

        // THEN
        verify(userCryptocurrencyRepository).save(userCryptocurrency);

    }

    @Test
    public void pay_paymentFailed() {
        // GIVEN
        CryptocurrencyPaymentDto payment = CommonMockObjects.mockCryptocurrencyPaymentDto();
        User user = CommonMockObjects.mockUser();
        UserCryptocurrency userCryptocurrency = CommonMockObjects.mockUserCryptocurrency();
        when(userService.currentUser()).thenReturn(user);
        when(userCryptocurrencyRepository.findByUserIdAndCryptocurrencyCode(user.getId(), payment.code()))
                .thenThrow(new RuntimeException());

        // WHEN
        assertThrows(RuntimeException.class, () -> service.pay(payment));

        // THEN
        verify(userCryptocurrencyRepository, times(0)).save(userCryptocurrency);

    }

    @Test
    public void pay_validationFailed_amountHigherThanBalance() {
        // GIVEN
        CryptocurrencyPaymentDto payment = new CryptocurrencyPaymentDto("TST", BigDecimal.valueOf(100000.00));
        User user = CommonMockObjects.mockUser();
        UserCryptocurrency userCryptocurrency = CommonMockObjects.mockUserCryptocurrency();
        when(userService.currentUser()).thenReturn(user);
        when(userCryptocurrencyRepository.findByUserIdAndCryptocurrencyCode(user.getId(), payment.code()))
                .thenReturn(Optional.of(userCryptocurrency));

        // WHEN
        assertThrows(ValidationException.class, () -> service.pay(payment));

        // THEN
        verify(userCryptocurrencyRepository, times(0)).save(userCryptocurrency);

    }

}
