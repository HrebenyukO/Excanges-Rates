package com.example.ExchangeRates.Service.Currency;


import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.Mappers.DtoMapper.EntityToDtoMapper;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositoryMB;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositoryMB;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.ExchangeRates.Util.LogColorConstants.*;

@Service
@Slf4j
public class MonoBankCurrencyBeanService implements CurrencyService{
    @Autowired
    CurrencyOnlineMapper entityMapper;
    @Autowired
    OnlineEuroRepositoryMB onlineEuroRepositoryMB;
    @Autowired
    OnlineDollarRepositoryMB onlineDollarRepositoryMB;
    @Autowired
    EntityToDtoMapper dtoMapper;
    @Transactional
    public OnlineDollarMonobank createOrUpdateOnlineDollar() {
        OnlineDollarMonobank entity = entityMapper.mapToEntity(OnlineDollarMonobank.class);
        OnlineDollarMonobank existingEntity = onlineDollarRepositoryMB.findFirstByOrderByIdDesc();

        if (existingEntity != null && isSameDate(entity.getDate(),existingEntity.getDate())) {
            if (entity.getOnlinePurchaseDollar()==existingEntity.getOnlinePurchaseDollar()&&
                    entity.getOnlineSaleDollar()==existingEntity.getOnlineSaleDollar()) {
                log.info(ANSI_BLUE+ "No changes detected. Entity Dollar Monobank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+ " Update entity Dollar Monobank"+ANSI_RESET);
                updateDollarEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+"Save Dollar Monobank for date " + entity.getDate()+ANSI_RESET);
            onlineDollarRepositoryMB.save(entity);
            return entity;
        }
    }
    private void updateDollarEntity(OnlineDollarMonobank existingEntity, OnlineDollarMonobank newEntity) {
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
    public OnlineEuroMonoBank createOrUpdateOnlineEuro() {
        OnlineEuroMonoBank entity = entityMapper.mapToEntity(OnlineEuroMonoBank.class);

        OnlineEuroMonoBank existingEntity = onlineEuroRepositoryMB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getOnlinePurchaseEuro()==existingEntity.getOnlinePurchaseEuro() &&
                    entity.getOnlineSaleEuro()==existingEntity.getOnlineSaleEuro()) {
                log.info(ANSI_BLUE+"No changes detected. Entity Euro Monobank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+" Update entity Euro Monobank"+ANSI_RESET);
                updateEuroEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+" Save Euro Monobank for date " + entity.getDate()+ANSI_RESET);
            onlineEuroRepositoryMB.save(entity);
            return entity;
        }
    }
    private void updateEuroEntity(OnlineEuroMonoBank existingEntity, OnlineEuroMonoBank newEntity) {

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

    public CurrencyOnlineDTO createDto() {
        OnlineDollarMonobank dollarMonobank = getLastOnlineDollar();
        OnlineEuroMonoBank euroMonoBank = getLstOnlineEuro();
        CurrencyOnlineDTO monoDTO = dtoMapper.entityMonoToDto(dollarMonobank, euroMonoBank);
        return monoDTO;
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
