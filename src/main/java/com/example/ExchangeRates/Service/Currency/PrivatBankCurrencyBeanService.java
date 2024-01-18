package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositoryPB;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositoryPB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<OnlineDollarPrivatBank> findAllOnlineDollarPB(){
        return onlineDollarRepositoryPB.findAll();
    }
    public List<OnlineEuroPrivatBank> findAllOnlineEuroPB(){
        return onlineEuroRepositoryPB.findAll();
    }
}
