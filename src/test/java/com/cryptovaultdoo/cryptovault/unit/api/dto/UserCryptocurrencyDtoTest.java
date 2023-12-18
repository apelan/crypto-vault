package com.cryptovaultdoo.cryptovault.unit.api.dto;

import com.cryptovaultdoo.cryptovault.unit.CommonMockObjects;
import com.cryptovaultdoo.cryptovault.api.dto.UserCryptocurrencyDto;
import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserCryptocurrencyDtoTest {

    @Test
    public void fromEntity() {
        // GIVEN
        UserCryptocurrency entity = CommonMockObjects.mockUserCryptocurrency();

        // WHEN
        UserCryptocurrencyDto dto = UserCryptocurrencyDto.fromEntity(entity);

        // THEN
        assertNotNull(dto);
        assertEquals(entity.getCryptocurrency().getName(), dto.name());
        assertEquals(entity.getCryptocurrency().getCode(), dto.code());
        assertEquals(entity.getAmount(), dto.amount());
    }

    @Test
    public void toEntity() {
        // GIVEN
        UserCryptocurrencyDto dto = new UserCryptocurrencyDto(
                "Test currency",
                "TST",
                BigDecimal.valueOf(100.00)
        );

        // WHEN
        UserCryptocurrency entity = dto.toEntity();

        // THEN
        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(dto.name(), entity.getCryptocurrency().getName());
        assertEquals(dto.code(), entity.getCryptocurrency().getCode());
        assertEquals(dto.amount(), entity.getAmount());

    }

}
