package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroMonoBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollarRepositoryMB;
import com.example.ExchangeRates.Repository.OnlineEuroRepositoryMB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MonoBankCurrencyBeanService {
    @Autowired
    CurrencyOnlineMapper entityMapper;
    @Autowired
    OnlineEuroRepositoryMB onlineEuroRepositoryMB;
    @Autowired
    OnlineDollarRepositoryMB onlineDollarRepositoryMB;

    public OnlineDollarMonobank createOnlineDollar(){
        OnlineDollarMonobank entity=entityMapper.mapToEntity(OnlineDollarMonobank.class);
        onlineDollarRepositoryMB.save(entity);
        log.info("Save currency Online Dollar Monobank");
        return entity;
    }
    public OnlineEuroMonoBank createOnlineEuro(){
        OnlineEuroMonoBank entity=entityMapper.mapToEntity(OnlineEuroMonoBank.class);
        onlineEuroRepositoryMB.save(entity);
        log.info("Save currency Online Euro Monobank");
        return entity;
    }
}
