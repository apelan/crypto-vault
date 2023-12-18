package com.cryptovaultdoo.cryptovault.unit.services;

import com.cryptovaultdoo.cryptovault.unit.CommonMockObjects;
import com.cryptovaultdoo.cryptovault.api.dto.SmartContractDto;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserSmartContract;
import com.cryptovaultdoo.cryptovault.data.repositories.SmartContractRepository;
import com.cryptovaultdoo.cryptovault.data.repositories.UserSmartContractRepository;
import com.cryptovaultdoo.cryptovault.services.UserDetailsService;
import com.cryptovaultdoo.cryptovault.services.SmartContractService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jakarta.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SmartContractServiceTest {

    @Mock
    private UserDetailsService userService;

    @Mock
    private SmartContractRepository smartContractRepository;

    @Mock
    private UserSmartContractRepository userSmartContractRepository;

    @InjectMocks
    private SmartContractService service;

    @Captor
    ArgumentCaptor<UserSmartContract> userSmartContractArgumentCaptor;

    @Test
    public void inspectContracts_returnValues() {
        // GIVEN
        when(userService.currentUser())
                .thenReturn(CommonMockObjects.mockUser());

        // WHEN
        List<SmartContractDto> resultList = service.inspectContracts();

        // THEN
        assertNotNull(resultList);
        assertEquals(1, resultList.size());

        SmartContractDto result = resultList.get(0);
        assertEquals("Test Smart Contract", result.name());
        assertEquals("0x000000000000000000000000000000000test123", result.contractAddress());
    }

    @Test
    public void inspectContracts_noValues() {
        // GIVEN
        User user = CommonMockObjects.mockUser();
        user.setSmartContracts(Collections.emptyList());
        when(userService.currentUser()).thenReturn(user);

        // WHEN
        List<SmartContractDto> resultList = service.inspectContracts();

        // THEN
        assertNotNull(resultList);
        assertEquals(0, resultList.size());
    }

    @Test
    public void depositContract_success() {
        // GIVEN
        User user = CommonMockObjects.mockUser();
        SmartContractDto dto = new SmartContractDto(
                "Test Smart Contract",
                "0x000000000000000000000000000000000test123"
        );

        when(userService.currentUser()).thenReturn(user);
        when(smartContractRepository.findByContractAddress(dto.contractAddress()))
                .thenReturn(Optional.empty());

        // WHEN
        service.depositContract(dto);

        // THEN
        verify(userSmartContractRepository).save(userSmartContractArgumentCaptor.capture());
    }

    @Test
    public void depositContract_contractExists_throwError() {
        // GIVEN
        User user = CommonMockObjects.mockUser();
        SmartContractDto dto = new SmartContractDto(
                "Test Smart Contract",
                "0x00000000000000000000000000000existing123"
        );

        when(userService.currentUser()).thenReturn(user);
        when(smartContractRepository.findByContractAddress(dto.contractAddress()))
                .thenReturn(Optional.of(CommonMockObjects.mockSmartContract()));

        // WHEN THEN
        assertThrows(ValidationException.class, () -> service.depositContract(dto));

        // THEN
        verify(userSmartContractRepository, times(0)).save(userSmartContractArgumentCaptor.capture());
    }

}
