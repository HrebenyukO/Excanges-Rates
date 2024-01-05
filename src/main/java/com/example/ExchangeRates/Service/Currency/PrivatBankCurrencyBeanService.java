package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollarRepositoryMB;
import com.example.ExchangeRates.Repository.OnlineDollarRepositoryPB;
import com.example.ExchangeRates.Repository.OnlineEuroRepositoryMB;
import com.example.ExchangeRates.Repository.OnlineEuroRepositoryPB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrivatBankCurrencyBeanService {
    @Autowired
    CurrencyOnlineMapper entityMapper;
    @Autowired
    OnlineDollarRepositoryPB onlineDollarRepositoryPB;
   @Autowired
    OnlineEuroRepositoryPB onlineEuroRepositoryPB;

    public OnlineDollarPrivatBank createOnlineDollar(){
        OnlineDollarPrivatBank entity=entityMapper.mapToEntity(OnlineDollarPrivatBank.class);
        onlineDollarRepositoryPB.save(entity);
        log.info("Save currency Online Dollar PB");
        return entity;
    }
    public OnlineEuroPrivatBank createOnlineEuro(){
        OnlineEuroPrivatBank entity=entityMapper.mapToEntity(OnlineEuroPrivatBank.class);
        onlineEuroRepositoryPB.save(entity);
        log.info("Save currency Online Euro PB");
        return entity;
    }
}
