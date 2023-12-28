package com.example.ExchangeRates.Mappers;

import com.example.ExchangeRates.Entity.Currency.NacBank;
import com.example.ExchangeRates.dto.NacBankDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class NacBankMapper implements Function<NacBank, NacBankDTO> {

    @Override
    public NacBankDTO apply(NacBank nacBank) {
        return new NacBankDTO(nacBank.getDollar(), nacBank.getEuro(), nacBank.getDate());
    }

    public NacBank apply(NacBankDTO dto){
        return new NacBank(dto.dollar(), dto.euro(), dto.date());
    }
}
