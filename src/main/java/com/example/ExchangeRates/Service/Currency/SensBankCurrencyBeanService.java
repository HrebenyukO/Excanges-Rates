package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarSensBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroSensBank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositorySB;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositorySB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.ExchangeRates.Util.LogColorConstants.*;
import static com.example.ExchangeRates.Util.LogColorConstants.ANSI_RESET;

@Service
@Slf4j
public class SensBankCurrencyBeanService implements CurrencyService {
    @Autowired
    CurrencyOnlineMapper entityMapper;
    @Autowired
    OnlineDollarRepositorySB dollarRepositorySB;
    @Autowired
    OnlineEuroRepositorySB euroRepositorySB;

    @Transactional
    public OnlineDollarSensBank createOrUpdateOnlineDollar() {
        OnlineDollarSensBank entity = entityMapper.mapToEntity(OnlineDollarSensBank.class);
        OnlineDollarSensBank existingEntity = dollarRepositorySB.findFirstByOrderByIdDesc();

        if (existingEntity != null && isSameDate(entity.getDate(),existingEntity.getDate())) {
            if (entity.getOnlinePurchaseDollar()==existingEntity.getOnlinePurchaseDollar()&&
                    entity.getOnlineSaleDollar()==existingEntity.getOnlineSaleDollar()) {
                log.info(ANSI_BLUE+ "No changes detected. Entity Dollar SenseBank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+ " Update entity SenseBank"+ANSI_RESET);
                updateDollarEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+"Save Dollar SenseBank for date " + entity.getDate()+ANSI_RESET);
            dollarRepositorySB.save(entity);
            return entity;
        }
    }
    private void updateDollarEntity(OnlineDollarSensBank existingEntity, OnlineDollarSensBank newEntity) {
        if (existingEntity.getOnlinePurchaseDollar() != newEntity.getOnlinePurchaseDollar()) {
            log.info("Update Online Purchase Dollar from " +
                    existingEntity.getOnlinePurchaseDollar() + " to " +
                    newEntity.getOnlinePurchaseDollar());
            existingEntity.setOnlinePurchaseDollar(newEntity.getOnlinePurchaseDollar());
        }
        if (existingEntity.getOnlineSaleDollar() != newEntity.getOnlineSaleDollar()) {
            log.info(" Update Online Sale Dollar from " +
                    existingEntity.getOnlineSaleDollar() + " to " +
                    newEntity.getOnlineSaleDollar());
            existingEntity.setOnlineSaleDollar(newEntity.getOnlineSaleDollar());
        }
    }

    @Transactional
    public OnlineEuroSensBank createOrUpdateOnlineEuro() {
        OnlineEuroSensBank entity = entityMapper.mapToEntity(OnlineEuroSensBank.class);

        OnlineEuroSensBank existingEntity = euroRepositorySB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getOnlinePurchaseEuro()==existingEntity.getOnlinePurchaseEuro() &&
                    entity.getOnlineSaleEuro()==existingEntity.getOnlineSaleEuro()) {
                log.info(ANSI_BLUE+"No changes detected. Entity Euro Sense Bank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+" Update entity Euro Sense Bank"+ANSI_RESET);
                updateEuroEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+" Save Euro Sense Bank for date " + entity.getDate()+ANSI_RESET);
            euroRepositorySB.save(entity);
            return entity;
        }
    }
    private void updateEuroEntity(OnlineEuroSensBank existingEntity, OnlineEuroSensBank newEntity) {

        if (existingEntity.getOnlinePurchaseEuro() != newEntity.getOnlinePurchaseEuro()) {
            log.info("Update Online Purchase Euro from " +
                    existingEntity.getOnlinePurchaseEuro() + " to " +
                    newEntity.getOnlinePurchaseEuro());
            existingEntity.setOnlinePurchaseEuro(newEntity.getOnlinePurchaseEuro());
        }

        if (existingEntity.getOnlineSaleEuro() != newEntity.getOnlineSaleEuro()) {
            log.info("Update Online Sale Euro from " +
                    existingEntity.getOnlineSaleEuro() + " to " +
                    newEntity.getOnlineSaleEuro());
            existingEntity.setOnlineSaleEuro(newEntity.getOnlineSaleEuro());
        }

    }
}
