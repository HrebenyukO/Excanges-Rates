package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.ArсhiveCurrency.PrivatBankCurrencyArchive;
import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashEuroPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Mappers.DtoMapper.EntityToDtoMapper;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyCashMapper;
import com.example.ExchangeRates.Mappers.EntityMapper.CurrencyOnlineMapper;
import com.example.ExchangeRates.Repository.ArchiveCurrency.ArchivePBRepository;
import com.example.ExchangeRates.Repository.CashDollar.CashDollarRepositoryPB;
import com.example.ExchangeRates.Repository.CashEuro.CashEuroRepositoryPB;
import com.example.ExchangeRates.Repository.OnlineDollar.OnlineDollarRepositoryPB;
import com.example.ExchangeRates.Repository.OnlineEuro.OnlineEuroRepositoryPB;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
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
    CurrencyOnlineMapper onlineMapper;
    @Autowired
    CurrencyCashMapper cashMapper;
    @Autowired
    OnlineDollarRepositoryPB onlineDollarRepositoryPB;
    @Autowired
    CashDollarRepositoryPB cashDollarRepository;
    @Autowired
    CashEuroRepositoryPB cashEuroRepositoryPB;
    @Autowired
    OnlineEuroRepositoryPB onlineEuroRepositoryPB;
    @Autowired
    ArchivePBRepository archivePBRepository;
    @Autowired
    EntityToDtoMapper dtoMapper;
    @Transactional
    public OnlineDollarPrivatBank createOrUpdateOnlineDollar() {
        OnlineDollarPrivatBank entity = onlineMapper.mapToEntity(OnlineDollarPrivatBank.class);
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
        OnlineEuroPrivatBank entity = onlineMapper.mapToEntity(OnlineEuroPrivatBank.class);
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

    public CurrencyOnlineDTO createDto() {
        OnlineDollarPrivatBank dollarPB = getLastOnlineDollar();
        OnlineEuroPrivatBank euroPB = getLstOnlineEuro();

        CurrencyOnlineDTO aBankDto = dtoMapper.entityPBToDto(dollarPB, euroPB);


        return aBankDto;
    }

    public OnlineDollarPrivatBank getLastOnlineDollar(){
        OnlineDollarPrivatBank entity=onlineDollarRepositoryPB.findFirstByOrderByIdDesc();
        return entity;
    }
    public OnlineEuroPrivatBank getLstOnlineEuro(){
        OnlineEuroPrivatBank entity=onlineEuroRepositoryPB.findFirstByOrderByIdDesc();
        return entity;
    }

    public List<PrivatBankCurrencyArchive> findAllArchivePB() {
        return archivePBRepository.findAll();
    }


    @Transactional
    public CashDollarPrivatBank createOrUpdateCashDollar() {
        CashDollarPrivatBank entity = cashMapper.mapToEntity(CashDollarPrivatBank.class);
        CashDollarPrivatBank existingEntity = cashDollarRepository.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getCashPurchaseDollar()==existingEntity.getCashPurchaseDollar()&&
                    entity.getCashSaleDollar()==existingEntity.getCashSaleDollar()) {
                log.info(ANSI_BLUE+" No changes detected. Entity Dollar PrivatBank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+"Update entity Dollar PrivatBank"+ANSI_RESET);
                updateDollarEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+"Save Dollar PrivatBank for date " + entity.getDate()+ANSI_RESET);
            cashDollarRepository.save(entity);
            return entity;
        }
    }

    private void updateDollarEntity(CashDollarPrivatBank existingEntity, CashDollarPrivatBank newEntity) {
        if (existingEntity.getCashPurchaseDollar() != newEntity.getCashPurchaseDollar()) {
            log.info("Update Online Purchase Dollar from " +
                    existingEntity.getCashPurchaseDollar() + " to " +
                    newEntity.getCashPurchaseDollar());
            existingEntity.setCashPurchaseDollar(newEntity.getCashPurchaseDollar());
        }
        if (existingEntity.getCashSaleDollar() != newEntity.getCashSaleDollar()) {
            log.info("Update Online Sale Dollar from " +
                    existingEntity.getCashSaleDollar() + " to " +
                    newEntity.getCashSaleDollar());
            existingEntity.setCashSaleDollar(newEntity.getCashSaleDollar());
        }
    }

    @Transactional
    public CashEuroPrivatBank createOrUpdateCashEuro() {
        CashEuroPrivatBank entity = cashMapper.mapToEntity(CashEuroPrivatBank.class);
        CashEuroPrivatBank existingEntity = cashEuroRepositoryPB.findFirstByOrderByIdDesc();
        if (existingEntity != null && isSameDate(entity.getDate(), existingEntity.getDate())) {
            if (entity.getCashPurchaseEuro()==existingEntity.getCashPurchaseEuro() &&
                    entity.getCashSaleEuro()==existingEntity.getCashSaleEuro()) {
                log.info(ANSI_BLUE+"No changes detected. Entity Euro PrivatBank remains unchanged."+ANSI_RESET);
                return existingEntity;
            } else {
                log.info(ANSI_YELLOW+"Update entity Euro PrivatBank"+ANSI_RESET);
                updateEuroEntity(existingEntity, entity);
                return existingEntity;
            }
        } else {
            log.info(ANSI_GREEN+"Save Euro PrivatBank for date " + entity.getDate()+ANSI_RESET);
            cashEuroRepositoryPB.save(entity);
            return entity;
        }
    }

    private void updateEuroEntity(CashEuroPrivatBank existingEntity, CashEuroPrivatBank newEntity) {
        if (existingEntity.getCashPurchaseEuro() != newEntity.getCashPurchaseEuro()) {
            log.info("Update Online Purchase Euro from " +
                    existingEntity.getCashPurchaseEuro() + " to " +
                    newEntity.getCashPurchaseEuro());
            existingEntity.setCashPurchaseEuro(newEntity.getCashPurchaseEuro());
        }

        if (existingEntity.getCashSaleEuro() != newEntity.getCashSaleEuro()) {
            log.info("Update Online Sale Euro from " +
                    existingEntity.getCashSaleEuro() + " to " +
                    newEntity.getCashSaleEuro());
            existingEntity.setCashSaleEuro(newEntity.getCashSaleEuro());
        }
    }

}
