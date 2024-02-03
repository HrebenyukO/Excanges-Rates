package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarUkrGazBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroUkrGazBank;
import com.example.ExchangeRates.Mappers.DtoMapper.EntityToDtoMapper;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositoryUkrGazBank;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositoryUkrGazBank;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.ExchangeRates.Util.LogColorConstants.*;
import static com.example.ExchangeRates.Util.LogColorConstants.ANSI_RESET;

@Service
@Slf4j
public class UkrGazBankCurrencyBeanService implements CurrencyService {
    @Autowired
    private CurrencyOnlineMapper entityMapper;
    @Autowired
    private OnlineEuroRepositoryUkrGazBank euroRepositoryUGB;
    @Autowired
    private OnlineDollarRepositoryUkrGazBank dollarRepositoryUGB;
    @Autowired
    EntityToDtoMapper dtoMapper;
    @Transactional
    public OnlineDollarUkrGazBank createOrUpdateOnlineDollar() {
        OnlineDollarUkrGazBank entity = entityMapper.mapToEntity(OnlineDollarUkrGazBank.class);
        OnlineDollarUkrGazBank existingEntity = dollarRepositoryUGB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getOnlinePurchaseDollar()==existingEntity.getOnlinePurchaseDollar()&&
                    entity.getOnlineSaleDollar()==existingEntity.getOnlineSaleDollar()) {
                log.info(ANSI_BLUE+"No changes detected. Entity Dollar UkrGazBank remains unchanged."+ANSI_RESET);

                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+"Update entity Dollar UkrGazBank"+ANSI_RESET);
                System.out.println(entity);
                updateDollarEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN +" Save Dollar UkrGazBank for date " + entity.getDate()+ANSI_RESET);
            dollarRepositoryUGB.save(entity);
            return entity;
        }
    }

    private void updateDollarEntity(OnlineDollarUkrGazBank existingEntity, OnlineDollarUkrGazBank newEntity) {
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
    public OnlineEuroUkrGazBank createOrUpdateOnlineEuro() {
        OnlineEuroUkrGazBank entity = entityMapper.mapToEntity(OnlineEuroUkrGazBank.class);

        OnlineEuroUkrGazBank existingEntity = euroRepositoryUGB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getOnlinePurchaseEuro()==existingEntity.getOnlinePurchaseEuro() &&
                    entity.getOnlineSaleEuro()==existingEntity.getOnlineSaleEuro()) {
                log.info(ANSI_BLUE+"No changes detected. Entity Euro UkrGazBank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+"Update entity Euro UkrGazBank"+ANSI_RESET);
                updateEuroEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+"Save Euro UkrGazBank for date " + entity.getDate()+ANSI_RESET);
            euroRepositoryUGB.save(entity);
            return entity;
        }
    }

    private void updateEuroEntity(OnlineEuroUkrGazBank existingEntity, OnlineEuroUkrGazBank newEntity) {
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

    public List<OnlineDollarUkrGazBank> findAllOnlineDollarUkrGazBank(){
        return dollarRepositoryUGB.findAll();
    }

    public List<OnlineEuroUkrGazBank> findAllOnlineEuroUkrGazBank(){
        return euroRepositoryUGB.findAll();
    }

    public CurrencyOnlineDTO createDto() {
        OnlineDollarUkrGazBank dollarUkrGazBank = getLastOnlineDollar();
        OnlineEuroUkrGazBank euroUkrGazBank = getLstOnlineEuro();

        CurrencyOnlineDTO ukrGazBankToDto =
                dtoMapper.entityUkrGazBankToDto(dollarUkrGazBank, euroUkrGazBank);


        return ukrGazBankToDto;
    }

    public OnlineDollarUkrGazBank getLastOnlineDollar(){
        OnlineDollarUkrGazBank entity= dollarRepositoryUGB.findFirstByOrderByIdDesc();
        return entity;
    }

    public OnlineEuroUkrGazBank getLstOnlineEuro(){
        OnlineEuroUkrGazBank entity= euroRepositoryUGB.findFirstByOrderByIdDesc();
        return entity;
    }
}
