package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarSensBank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositorySB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SensBankCurrencyBeanService {
    @Autowired
    CurrencyOnlineMapper entityMapper;
    @Autowired
    OnlineDollarRepositorySB dollarRepositorySB;

    public OnlineDollarSensBank createOnlineDollar(){
        OnlineDollarSensBank entity=entityMapper.mapToEntity(OnlineDollarSensBank.class);
        dollarRepositorySB.save(entity);
        log.info("Save currency Online Dollar Sens Bank");
        return entity;
    }
}
