package com.example.ExchangeRates.Mappers.DtoMapper;

import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashEuroPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.*;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.*;
import com.example.ExchangeRates.dto.CurrencyCashDTO;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.springframework.stereotype.Component;

@Component
public class EntityToDtoMapper {

    public CurrencyOnlineDTO entityMonoToDto(OnlineDollarMonobank dollarEntity, OnlineEuroMonoBank euroEntity) {
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

    public CurrencyOnlineDTO entityAbankToDto(OnlineDollarAbank dollarEntity, OnlineEuroAbank euroEntity) {
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

    public CurrencyOnlineDTO entityPBToDto(OnlineDollarPrivatBank dollarEntity, OnlineEuroPrivatBank euroEntity) {
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

    public CurrencyOnlineDTO entitySBToDto(OnlineDollarSensBank dollarEntity, OnlineEuroSensBank euroEntity) {
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

    public CurrencyOnlineDTO entityUkrGazBankToDto(OnlineDollarUkrGazBank dollarEntity, OnlineEuroUkrGazBank euroEntity) {
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

    public CurrencyCashDTO entityPBToDto(CashDollarPrivatBank dollarEntity, CashEuroPrivatBank euroEntity) {
        double cashPurchaseDollar = (dollarEntity != null) ? dollarEntity.getCashPurchaseDollar() : 0.0;
        double cashSaleDollar = (dollarEntity != null) ? dollarEntity.getCashSaleDollar() : 0.0;
        double cashPurchaseEuro = (euroEntity != null) ? euroEntity.getCashPurchaseEuro() : 0.0;
        double cashSaleEuro = (euroEntity != null) ? euroEntity.getCashSaleEuro() : 0.0;

        return new CurrencyCashDTO(
                cashPurchaseDollar,
                cashSaleDollar,
                cashPurchaseEuro,
                cashSaleEuro
        );
    }

}