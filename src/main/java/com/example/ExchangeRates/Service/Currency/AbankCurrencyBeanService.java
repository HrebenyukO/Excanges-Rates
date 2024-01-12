package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollarAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroMonoBank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollarRepositoryAB;
import com.example.ExchangeRates.Repository.OnlineEuroRepositoryAB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AbankCurrencyBeanService {
    @Autowired
   private CurrencyOnlineMapper entityMapper;
    @Autowired
    private OnlineEuroRepositoryAB euroRepositoryAB;
    @Autowired
    private OnlineDollarRepositoryAB dollarRepositoryAB;

    public OnlineDollarAbank createOnlineDollar(){
        OnlineDollarAbank entity=entityMapper.mapToEntity(OnlineDollarAbank.class);
        dollarRepositoryAB.save(entity);
        log.info("Save currency Online Dollar Abank");
        return entity;
    }
    public OnlineEuroAbank createOnlineEuro(){
        OnlineEuroAbank entity=entityMapper.mapToEntity(OnlineEuroAbank.class);
        euroRepositoryAB.save(entity);
        log.info("Save currency Online Euro Abank");
        return entity;
    }

}
