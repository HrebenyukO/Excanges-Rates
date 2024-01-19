package com.example.ExchangeRates.Mappers.DtoMapper;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityToDtoMapper {

    public CurrencyOnlineDTO entityToDto(OnlineDollarMonobank dollarEntity, OnlineEuroMonoBank euroEntity) {
        double onlinePurchaseDollar = (dollarEntity != null) ? dollarEntity.getOnlinePurchaseDollar() : 0.0;
        double onlineSaleDollar = (dollarEntity != null) ? dollarEntity.getOnlineSaleDollar() : 0.0;
        double onlinePurchaseEuro = (euroEntity != null) ? euroEntity.getOnlinePurchaseEuro() : 0.0;
        double onlineSaleEuro = (euroEntity != null) ? euroEntity.getOnlineSaleEuro() : 0.0;

        return new CurrencyOnlineDTO(
                onlinePurchaseDollar,
                onlineSaleDollar,
                onlinePurchaseEuro,
                onlineSaleEuro
        );
    }

}