package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositoryMB;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositoryMB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MonoBankCurrencyBeanService {
    @Autowired
    CurrencyOnlineMapper entityMapper;
    @Autowired
    OnlineEuroRepositoryMB onlineEuroRepositoryMB;
    @Autowired
    OnlineDollarRepositoryMB onlineDollarRepositoryMB;

    public OnlineDollarMonobank createAndSaveOnlineDollar(){
        OnlineDollarMonobank entity=entityMapper.mapToEntity(OnlineDollarMonobank.class);
        onlineDollarRepositoryMB.save(entity);
        log.info("Save currency Online Dollar Monobank");
        return entity;
    }
    public OnlineEuroMonoBank createAndSaveOnlineEuro(){
        OnlineEuroMonoBank entity=entityMapper.mapToEntity(OnlineEuroMonoBank.class);
        onlineEuroRepositoryMB.save(entity);
        log.info("Save currency Online Euro Monobank");
        return entity;
    }

    public OnlineDollarMonobank getLastOnlineDollar(){
        OnlineDollarMonobank entity=onlineDollarRepositoryMB.findFirstByOrderByIdDesc();
        return entity;
    }

    public OnlineEuroMonoBank getLstOnlineEuro(){
        OnlineEuroMonoBank entity=onlineEuroRepositoryMB.findFirstByOrderByIdDesc();
        return entity;
    }

    public List<OnlineDollarMonobank> findAllOnlineDollarMonobank(){
        return onlineDollarRepositoryMB.findAll();
    }

    public List<OnlineEuroMonoBank> findAllOnlineEuroMonobank(){
        return onlineEuroRepositoryMB.findAll();
    }
}
