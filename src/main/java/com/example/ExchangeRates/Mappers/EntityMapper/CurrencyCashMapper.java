package com.example.ExchangeRates.Mappers.EntityMapper;

import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashEuroPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.*;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.*;
import com.example.ExchangeRates.Mappers.DtoMapper.AbankDtoMapper;
import com.example.ExchangeRates.Mappers.DtoMapper.PrivatBankDtoMapper;
import com.example.ExchangeRates.Mappers.DtoMapper.SensBankDtoMapper;
import com.example.ExchangeRates.Mappers.DtoMapper.UkrGazBankDtoMapper;
import com.example.ExchangeRates.dto.CurrencyCashDTO;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CurrencyCashMapper {
    @Autowired
    PrivatBankDtoMapper privatBankDtoMapper;
    @Autowired
    AbankDtoMapper abankDtoMapper;
    @Autowired
    SensBankDtoMapper sensBankDtoMapper;
    @Autowired
    UkrGazBankDtoMapper ukrGazBankDtoMapper;
    private static final String PRIVATBANK = "PrivatBank";

    private static final String SENSBANK = "SensBank";
    private static final String ABANK = "aBank";
    private static final String UKRGAZBANK="UkrGazBank";

    public <T> T mapToEntity(Class<T> entityClass) {
        String bankName = resolveBank(entityClass);
        return createEntity(entityClass, bankName);
    }
    private <T> String resolveBank(Class<T> entityClass) {
        if (entityClass == CashDollarPrivatBank.class || entityClass == CashEuroPrivatBank.class) {
            return PRIVATBANK;
        }  else if(entityClass== OnlineDollarAbank.class || entityClass== OnlineEuroAbank.class){
            return ABANK;
        }
        else if(entityClass== OnlineDollarSensBank.class || entityClass== OnlineEuroSensBank.class) {
            return SENSBANK;
        }
        else if(entityClass== OnlineDollarUkrGazBank.class || entityClass== OnlineEuroUkrGazBank.class) {
            return UKRGAZBANK;
        }else {
            throw new IllegalArgumentException("Unsupported entity type");
        }
    }
    private <T> T createEntity(Class<T> entityClass, String bankName) {
        Date currentDate = new Date(System.currentTimeMillis());

        if (entityClass == CashDollarPrivatBank.class) {
            CurrencyCashDTO currencyCashDTO = privatBankDtoMapper.mapToCurrencyCashDto();
            return (T) new CashDollarPrivatBank(
                    bankName,
                    currencyCashDTO.cashDollarPurchase(),
                    currencyCashDTO.cashDollarSales(),
                    currentDate);
        } else if (entityClass == CashEuroPrivatBank.class) {
            CurrencyCashDTO currencyCashDTO = privatBankDtoMapper.mapToCurrencyCashDto();
            return (T) new CashEuroPrivatBank(
                    bankName,
                    currencyCashDTO.cashEuroPurchase(),
                    currencyCashDTO.cashEuroSales(),
                    currentDate);
        }
        else {
            throw new IllegalArgumentException("Unsupported entity type");
        }
    }
}
