package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroAbank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositoryAB;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositoryAB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<OnlineDollarAbank> findAllOnlineDollarAbank(){
        return dollarRepositoryAB.findAll();
    }

    public List<OnlineEuroAbank> findAllOnlineEuroAbank(){
        return euroRepositoryAB.findAll();
    }

}
