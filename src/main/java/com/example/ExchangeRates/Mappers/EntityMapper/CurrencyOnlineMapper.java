package com.example.ExchangeRates.Mappers.EntityMapper;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.*;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.*;
import com.example.ExchangeRates.Mappers.DtoMapper.*;
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
    @Autowired
    AbankDtoMapper abankDtoMapper;
    @Autowired
    SensBankDtoMapper sensBankDtoMapper;
    @Autowired
    UkrGazBankDtoMapper ukrGazBankDtoMapper;
    private static final String PRIVATBANK = "PrivatBank";
    private static final String MONOBANK = "MonoBank";
    private static final String SENSBANK = "SensBank";
    private static final String ABANK = "aBank";

    private static final String UKRGAZBANK="UkrGazBank";

    public <T> T mapToEntity(Class<T> entityClass) {
        String bankName = resolveBank(entityClass);
        return createEntity(entityClass, bankName);
    }
    private <T> String resolveBank(Class<T> entityClass) {
        if (entityClass == OnlineDollarPrivatBank.class || entityClass == OnlineEuroPrivatBank.class) {
            return PRIVATBANK;
        } else if (entityClass == OnlineDollarMonobank.class || entityClass == OnlineEuroMonoBank.class) {
            return MONOBANK;
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
        } else if(entityClass==OnlineDollarAbank.class){
            CurrencyOnlineDTO currencyOnlineDTO=abankDtoMapper.parseCurrencyOnline();
            return (T) new OnlineDollarAbank(
                    bankName,
                    currencyOnlineDTO.onlineDollarPurchase(),
                    currencyOnlineDTO.onlineDollarSales(),
                    currentDate);
        } else if(entityClass==OnlineEuroAbank.class){
            CurrencyOnlineDTO currencyOnlineDTO=abankDtoMapper.parseCurrencyOnline();
            return (T) new OnlineEuroAbank(
                    bankName,
                    currencyOnlineDTO.onlineEuroPurchase(),
                    currencyOnlineDTO.onlineEuroSales(),
                    currentDate);
        }else if(entityClass== OnlineDollarSensBank.class){
            CurrencyOnlineDTO currencyOnlineDTO=sensBankDtoMapper.parseCurrencyOnline();
            return (T) new OnlineDollarSensBank(
                    bankName,
                    currencyOnlineDTO.onlineDollarPurchase(),
                    currencyOnlineDTO.onlineDollarSales(),
                    currentDate);}
        else if(entityClass== OnlineEuroSensBank.class){
            CurrencyOnlineDTO currencyOnlineDTO=sensBankDtoMapper.parseCurrencyOnline();
            return (T) new OnlineEuroSensBank(
                    bankName,
                    currencyOnlineDTO.onlineEuroPurchase(),
                    currencyOnlineDTO.onlineEuroSales(),
                    currentDate);}
        else if(entityClass== OnlineDollarUkrGazBank.class){
            CurrencyOnlineDTO currencyOnlineDTO=ukrGazBankDtoMapper.parseCurrencyOnline();
            return (T) new OnlineDollarUkrGazBank(
                    bankName,
                    currencyOnlineDTO.onlineDollarPurchase(),
                    currencyOnlineDTO.onlineDollarSales(),
                    currentDate);}
        else if(entityClass== OnlineEuroUkrGazBank.class){
            CurrencyOnlineDTO currencyOnlineDTO=ukrGazBankDtoMapper.parseCurrencyOnline();
            return (T) new OnlineEuroUkrGazBank(
                    bankName,
                    currencyOnlineDTO.onlineEuroPurchase(),
                    currencyOnlineDTO.onlineEuroSales(),
                    currentDate);}
        else {
            throw new IllegalArgumentException("Unsupported entity type");
        }
    }
}