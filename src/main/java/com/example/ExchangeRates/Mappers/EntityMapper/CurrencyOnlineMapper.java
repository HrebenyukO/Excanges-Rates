package com.example.ExchangeRates.Mappers.EntityMapper;

import com.example.ExchangeRates.Entity.Currency.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroMonoBank;
import com.example.ExchangeRates.Mappers.DtoMapper.MonoBankDtoMapper;
import com.example.ExchangeRates.Mappers.DtoMapper.PrivatBankDtoMapper;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CurrencyOnlineMapper {

    @Autowired
    PrivatBankDtoMapper privatBankDtoMapper;
    @Autowired
    MonoBankDtoMapper monoBankDtoMapper;
    private static final String PRIVATBANK = "PrivatBank";
    private static final String MONOBANK = "MonoBank";

    public <T> T mapToEntity(Class<T> entityClass) {
        String bankName = resolveBank(entityClass);
        return createEntity(entityClass, bankName);
    }
    private <T> String resolveBank(Class<T> entityClass) {
        if (entityClass == OnlineDollarPrivatBank.class || entityClass == OnlineEuroPrivatBank.class) {
            return PRIVATBANK;
        } else if (entityClass == OnlineDollarMonobank.class || entityClass == OnlineEuroMonoBank.class) {
            return MONOBANK;
        } else {
            throw new IllegalArgumentException("Unsupported entity type");
        }
    }
    private <T> T createEntity(Class<T> entityClass, String bankName) {
        Date currentDate = new Date(System.currentTimeMillis());

        if (entityClass == OnlineDollarPrivatBank.class) {
            CurrencyOnlineDTO currencyOnlineDTO = privatBankDtoMapper.mapToCurrencyOnlineDto();
            return (T) new OnlineDollarPrivatBank(
                    bankName,
                    currencyOnlineDTO.onlineDollarPurchase(),
                    currencyOnlineDTO.onlineDollarSales(),
                    currentDate);
        } else if (entityClass == OnlineEuroPrivatBank.class) {
            CurrencyOnlineDTO currencyOnlineDTO = privatBankDtoMapper.mapToCurrencyOnlineDto();
            return (T) new OnlineEuroPrivatBank(
                    bankName,
                    currencyOnlineDTO.onlineEuroPurchase(),
                    currencyOnlineDTO.onlineEuroSales(),
                    currentDate);
        } else if (entityClass == OnlineDollarMonobank.class) {
            CurrencyOnlineDTO currencyOnlineDTO = monoBankDtoMapper.mapToCurrencyOnlineDto();
            return (T) new OnlineDollarMonobank(
                    bankName,
                    currencyOnlineDTO.onlineDollarPurchase(),
                    currencyOnlineDTO.onlineDollarSales(),
                    currentDate);
        } else if (entityClass == OnlineEuroMonoBank.class) {
            CurrencyOnlineDTO currencyOnlineDTO = monoBankDtoMapper.mapToCurrencyOnlineDto();
            return (T) new OnlineEuroMonoBank(
                    bankName,
                    currencyOnlineDTO.onlineEuroPurchase(),
                    currencyOnlineDTO.onlineEuroSales(),
                    currentDate);
        } else {
            throw new IllegalArgumentException("Unsupported entity type");
        }
    }
}