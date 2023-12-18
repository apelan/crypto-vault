package com.cryptovaultdoo.cryptovault.unit.services;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.api.enumerator.PaymentType;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.services.PaymentService;
import com.cryptovaultdoo.cryptovault.services.UserDetailsService;
import com.cryptovaultdoo.cryptovault.unit.CommonMockObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private UserDetailsService userService;

    @Mock
    private UserCryptocurrencyRepository userCryptocurrencyRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void payWithCryptocurrency() {
        // GIVEN
        User user = CommonMockObjects.mockUser();
        CryptocurrencyPaymentDto payment = new CryptocurrencyPaymentDto("TST", BigDecimal.valueOf(0.10));
        when(userService.currentUser()).thenReturn(user);
        when(userCryptocurrencyRepository.findByUserIdAndCryptocurrencyCode(user.getId(), payment.code()))
                .thenReturn(Optional.of(CommonMockObjects.mockUserCryptocurrency()));

        // WHEN
        assertDoesNotThrow(() -> paymentService.payWithCryptocurrency(PaymentType.BINANCE, payment));

    }

}
