package com.cryptovaultdoo.cryptovault.unit.api.dto;

import com.cryptovaultdoo.cryptovault.unit.CommonMockObjects;
import com.cryptovaultdoo.cryptovault.api.dto.SmartContractDto;
import com.cryptovaultdoo.cryptovault.data.entities.SmartContract;
import com.cryptovaultdoo.cryptovault.data.entities.UserSmartContract;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SmartContractDtoTest {

    @Test
    public void fromEntity() {
        // GIVEN
        UserSmartContract entity = CommonMockObjects.mockUserSmartContract(CommonMockObjects.mockUser());

        // WHEN
        SmartContractDto dto = SmartContractDto.fromEntity(entity);

        // THEN
        assertNotNull(dto);
        assertEquals(entity.getSmartContract().getName(), dto.name());
        assertEquals(entity.getSmartContract().getContractAddress(), dto.contractAddress());

    }

    @Test
    public void toEntity() {
        // GIVEN
        SmartContractDto dto = new SmartContractDto(
                "Test Smart Contract",
                "0x000000000000000000000000000000000test123"
        );

        // WHEN
        SmartContract entity = dto.toEntity();

        // THEN
        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.contractAddress(), entity.getContractAddress());

    }

}
