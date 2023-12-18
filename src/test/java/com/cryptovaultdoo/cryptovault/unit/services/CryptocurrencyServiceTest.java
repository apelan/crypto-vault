package com.cryptovaultdoo.cryptovault.unit.services;

import com.cryptovaultdoo.cryptovault.unit.CommonMockObjects;
import com.cryptovaultdoo.cryptovault.api.dto.UserCryptocurrencyDto;
import com.cryptovaultdoo.cryptovault.data.entities.Cryptocurrency;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;
import com.cryptovaultdoo.cryptovault.data.repositories.CryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.services.UserDetailsService;
import com.cryptovaultdoo.cryptovault.services.CryptocurrencyService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CryptocurrencyServiceTest {

    @Mock
    private UserDetailsService userService;

    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Mock
    private UserCryptocurrencyRepository userCryptocurrencyRepository;

    @InjectMocks
    private CryptocurrencyService service;

    @Captor
    private ArgumentCaptor<UserCryptocurrency> userCryptocurrencyArgumentCaptor;

    @Test
    public void inspectCryptocurrencies_returnValues() {
        // GIVEN
        when(userService.currentUser())
                .thenReturn(CommonMockObjects.mockUser());

        // WHEN
        List<UserCryptocurrencyDto> resultList = service.inspectCryptocurrencies();

        // THEN
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        UserCryptocurrencyDto result = resultList.get(0);
        assertEquals("Test currency", result.name());
        assertEquals("TST", result.code());
        assertEquals(BigDecimal.valueOf(100.00), result.amount());
    }

    @Test
    public void inspectCryptocurrencies_noValues() {
        // GIVEN
        User user = CommonMockObjects.mockUser();
        user.setCryptocurrencies(Collections.emptyList());
        when(userService.currentUser()).thenReturn(user);

        // WHEN
        List<UserCryptocurrencyDto> resultList = service.inspectCryptocurrencies();

        // THEN
        assertNotNull(resultList);
        assertEquals(0, resultList.size());
    }

    @Test
    public void depositCryptocurrency_existingCryptocurrency() {
        // GIVEN
        User user = CommonMockObjects.mockUser();
        UserCryptocurrencyDto dto =
                new UserCryptocurrencyDto("Test currency", "TST", BigDecimal.valueOf(50.00));
        Cryptocurrency cryptocurrency = CommonMockObjects.mockCryptocurrency();
        UserCryptocurrency userCryptocurrency = CommonMockObjects.mockUserCryptocurrency();

        when(userService.currentUser()).thenReturn(user);
        when(cryptocurrencyRepository.findByCode(dto.code()))
                .thenReturn(Optional.of(cryptocurrency));
        when(userCryptocurrencyRepository.findByUserIdAndCryptocurrencyId(user.getId(), cryptocurrency.getId()))
                .thenReturn(Optional.of(userCryptocurrency));

        // WHEN
        service.depositCryptocurrency(dto);

        // THEN
        assertEquals(BigDecimal.valueOf(150.00), userCryptocurrency.getAmount());
        verify(userCryptocurrencyRepository).save(userCryptocurrency);

    }

    @Test
    public void depositCryptocurrency_newCryptocurrency() {
        // GIVEN
        User user = CommonMockObjects.mockUser();
        UserCryptocurrencyDto dto =
                new UserCryptocurrencyDto("Test currency", "TST", BigDecimal.valueOf(100.00));
        Cryptocurrency cryptocurrency = CommonMockObjects.mockCryptocurrency();
        UserCryptocurrency userCryptocurrency = CommonMockObjects.mockUserCryptocurrency();

        when(userService.currentUser()).thenReturn(user);
        when(cryptocurrencyRepository.findByCode(dto.code()))
                .thenReturn(Optional.of(cryptocurrency));
        when(userCryptocurrencyRepository.findByUserIdAndCryptocurrencyId(user.getId(), cryptocurrency.getId()))
                .thenReturn(Optional.empty());

        // WHEN
        service.depositCryptocurrency(dto);

        // THEN
        assertEquals(BigDecimal.valueOf(100.00), userCryptocurrency.getAmount());
        verify(userCryptocurrencyRepository).save(userCryptocurrencyArgumentCaptor.capture());

    }

    @ParameterizedTest
    @MethodSource("userCryptocurrenciesTestCases")
    public void depositCryptocurrency_depositMultiple_assertResult(
            UserCryptocurrencyDto dto, BigDecimal expectedResult
    ) {
        // GIVEN
        User user = CommonMockObjects.mockUser();
        Cryptocurrency cryptocurrency = CommonMockObjects.mockCryptocurrency();
        UserCryptocurrency userCryptocurrency = CommonMockObjects.mockUserCryptocurrency();
        userCryptocurrency.setUser(user);

        when(userService.currentUser()).thenReturn(user);
        when(cryptocurrencyRepository.findByCode(dto.code()))
                .thenReturn(Optional.of(cryptocurrency));
        when(userCryptocurrencyRepository.findByUserIdAndCryptocurrencyId(user.getId(), cryptocurrency.getId()))
                .thenReturn(Optional.of(userCryptocurrency));

        // WHEN
        service.depositCryptocurrency(dto);

        // THEN
        assertEquals(expectedResult, userCryptocurrency.getAmount());
        verify(userCryptocurrencyRepository).save(userCryptocurrency);

    }

    private static Stream<Arguments> userCryptocurrenciesTestCases() {
        UserCryptocurrencyDto dto1 = new UserCryptocurrencyDto("Test currency", "TST", BigDecimal.valueOf(50.00));
        UserCryptocurrencyDto dto2 = new UserCryptocurrencyDto("Test currency", "TST", BigDecimal.valueOf(10.10));
        UserCryptocurrencyDto dto3 = new UserCryptocurrencyDto("Test currency", "TST", BigDecimal.valueOf(5.20));
        return Stream.of(
                Arguments.of(dto1, BigDecimal.valueOf(150.0)),
                Arguments.of(dto2, BigDecimal.valueOf(110.10)),
                Arguments.of(dto3, BigDecimal.valueOf(105.20))
        );
    }


}
