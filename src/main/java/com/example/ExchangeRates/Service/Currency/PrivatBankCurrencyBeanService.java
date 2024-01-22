package com.example.ExchangeRates.Service.Currency;

import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositoryPB;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositoryPB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.ExchangeRates.Util.LogColorConstants.*;

@Service
@Slf4j
public class PrivatBankCurrencyBeanService implements CurrencyService {
    @Autowired
    CurrencyOnlineMapper entityMapper;
    @Autowired
    OnlineDollarRepositoryPB onlineDollarRepositoryPB;
    @Autowired
    OnlineEuroRepositoryPB onlineEuroRepositoryPB;
    @Transactional
    public OnlineDollarPrivatBank createOrUpdateOnlineDollar() {
        OnlineDollarPrivatBank entity = entityMapper.mapToEntity(OnlineDollarPrivatBank.class);
        OnlineDollarPrivatBank existingEntity = onlineDollarRepositoryPB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getOnlinePurchaseDollar()==existingEntity.getOnlinePurchaseDollar()&&
            entity.getOnlineSaleDollar()==existingEntity.getOnlineSaleDollar()) {
                log.info(ANSI_BLUE+" No changes detected. Entity Dollar PrivatBank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+"Update entity Dollar PrivatBank"+ANSI_RESET);
                updateDollarEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+"Save Dollar PrivatBank for date " + entity.getDate()+ANSI_RESET);
            onlineDollarRepositoryPB.save(entity);
            return entity;
        }
    }

    private void updateDollarEntity(OnlineDollarPrivatBank existingEntity, OnlineDollarPrivatBank newEntity) {
        if (existingEntity.getOnlinePurchaseDollar() != newEntity.getOnlinePurchaseDollar()) {
            log.info("Update Online Purchase Dollar from " +
                    existingEntity.getOnlinePurchaseDollar() + " to " +
                    newEntity.getOnlinePurchaseDollar());
            existingEntity.setOnlinePurchaseDollar(newEntity.getOnlinePurchaseDollar());
        }
        if (existingEntity.getOnlineSaleDollar() != newEntity.getOnlineSaleDollar()) {
            log.info("Update Online Sale Dollar from " +
                    existingEntity.getOnlineSaleDollar() + " to " +
                    newEntity.getOnlineSaleDollar());
            existingEntity.setOnlineSaleDollar(newEntity.getOnlineSaleDollar());
        }
    }
    @Transactional
    public OnlineEuroPrivatBank createOrUpdateOnlineEuro() {
        OnlineEuroPrivatBank entity = entityMapper.mapToEntity(OnlineEuroPrivatBank.class);
        OnlineEuroPrivatBank existingEntity = onlineEuroRepositoryPB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getOnlinePurchaseEuro()==existingEntity.getOnlinePurchaseEuro() &&
            entity.getOnlineSaleEuro()==existingEntity.getOnlineSaleEuro()) {
                log.info(ANSI_BLUE+"No changes detected. Entity Euro PrivatBank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+"Update entity Euro PrivatBank"+ANSI_RESET);
                updateEuroEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+"Save Euro PrivatBank for date " + entity.getDate()+ANSI_RESET);
            onlineEuroRepositoryPB.save(entity);
            return entity;
        }
    }

    private void updateEuroEntity(OnlineEuroPrivatBank existingEntity, OnlineEuroPrivatBank newEntity) {
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

    public List<OnlineDollarPrivatBank> findAllOnlineDollarPB(){
        return onlineDollarRepositoryPB.findAll();
    }
    public List<OnlineEuroPrivatBank> findAllOnlineEuroPB(){
        return onlineEuroRepositoryPB.findAll();
    }
}
