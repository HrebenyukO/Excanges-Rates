package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroAbank;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositoryAB;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositoryAB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.example.ExchangeRates.Util.LogColorConstants.*;

@Service
@Slf4j
public class AbankCurrencyBeanService implements CurrencyService {
    @Autowired
   private CurrencyOnlineMapper entityMapper;
    @Autowired
    private OnlineEuroRepositoryAB euroRepositoryAB;
    @Autowired
    private OnlineDollarRepositoryAB dollarRepositoryAB;

    @Transactional
    public OnlineDollarAbank createOrUpdateOnlineDollar() {
        OnlineDollarAbank entity = entityMapper.mapToEntity(OnlineDollarAbank.class);
        OnlineDollarAbank existingEntity = dollarRepositoryAB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getOnlinePurchaseDollar()==existingEntity.getOnlinePurchaseDollar()&&
                    entity.getOnlineSaleDollar()==existingEntity.getOnlineSaleDollar()) {
                log.info(ANSI_BLUE+"No changes detected. Entity Dollar Abank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+"Update entity Dollar Abank"+ANSI_RESET);
                updateDollarEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN +" Save Dollar Abank for date " + entity.getDate()+ANSI_RESET);
            dollarRepositoryAB.save(entity);
            return entity;
        }
    }

    private void updateDollarEntity(OnlineDollarAbank existingEntity, OnlineDollarAbank newEntity) {
        if (existingEntity.getOnlinePurchaseDollar() != newEntity.getOnlinePurchaseDollar()) {
            log.info(" Update Online Purchase Dollar from " +
                    existingEntity.getOnlinePurchaseDollar() + " to " +
                    newEntity.getOnlinePurchaseDollar());
            existingEntity.setOnlinePurchaseDollar(newEntity.getOnlinePurchaseDollar());
        }

        // Аналогично для других полей, если необходимо
        if (existingEntity.getOnlineSaleDollar() != newEntity.getOnlineSaleDollar()) {
            log.info(" Update Online Sale Dollar from " +
                    existingEntity.getOnlineSaleDollar() + " to " +
                    newEntity.getOnlineSaleDollar());
            existingEntity.setOnlineSaleDollar(newEntity.getOnlineSaleDollar());
        }
    }
    @Transactional
    public OnlineEuroAbank createOrUpdateOnlineEuro() {
        OnlineEuroAbank entity = entityMapper.mapToEntity(OnlineEuroAbank.class);

        OnlineEuroAbank existingEntity = euroRepositoryAB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getOnlinePurchaseEuro()==existingEntity.getOnlinePurchaseEuro() &&
                    entity.getOnlineSaleEuro()==existingEntity.getOnlineSaleEuro()) {
                log.info(ANSI_BLUE+"No changes detected. Entity Euro Abank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+"Update entity Euro Abank"+ANSI_RESET);
                updateEuroEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+"Save Euro Abank for date " + entity.getDate()+ANSI_RESET);
            euroRepositoryAB.save(entity);
            return entity;
        }
    }

    private void updateEuroEntity(OnlineEuroAbank existingEntity, OnlineEuroAbank newEntity) {
        if (existingEntity.getOnlinePurchaseEuro() != newEntity.getOnlinePurchaseEuro()) {
            log.info("Update Online Purchase Euro from " +
                    existingEntity.getOnlinePurchaseEuro() + " to " +
                    newEntity.getOnlinePurchaseEuro());
            existingEntity.setOnlinePurchaseEuro(newEntity.getOnlinePurchaseEuro());
        }
        if (existingEntity.getOnlineSaleEuro()!= newEntity.getOnlineSaleEuro()){
            log.info("Update Online Purchase Euro from "+existingEntity.getOnlineSaleEuro()+" to "+
                    newEntity.getOnlineSaleEuro());
            existingEntity.setOnlineSaleEuro(newEntity.getOnlineSaleEuro());
        }
    }

    public List<OnlineDollarAbank> findAllOnlineDollarAbank(){
        return dollarRepositoryAB.findAll();
    }

    public List<OnlineEuroAbank> findAllOnlineEuroAbank(){
        return euroRepositoryAB.findAll();
    }

}
