package com.example.ExchangeRates.Mappers.EntityMapper;

import com.example.ExchangeRates.Entity.Currency.NacBank;
import com.example.ExchangeRates.Mappers.DtoMapper.NacBankDtoMapper;
import com.example.ExchangeRates.dto.NacBankDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NacBankEntityMapper   {
    private final NacBankDtoMapper nacBankDtoMapper;
    public NacBank mapToNacBank() {
        NacBankDTO dto=nacBankDtoMapper.mapToNacBankDTO();
        return new NacBank(dto.dollar(), dto.euro(), dto.date());
    }
}
