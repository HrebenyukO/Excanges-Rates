package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.NacBank;
import com.example.ExchangeRates.Mappers.EntityMapper.NacBankEntityMapper;
import com.example.ExchangeRates.Repository.NacBankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ExchangeRates.Util.LogColorConstants.ANSI_GREEN;
import static com.example.ExchangeRates.Util.LogColorConstants.ANSI_RESET;

@Service
@Slf4j
public class NacBankCurrencyBeanService implements CurrencyService{
    @Autowired
    NacBankEntityMapper entityMapper;
    @Autowired
    NacBankRepository nacBankRepository;
    public NacBank create(){
        NacBank entity=entityMapper.mapToNacBank();
        NacBank existEntity=nacBankRepository.findFirstByOrderByIdDesc();

        if(isSameDate(entity.getDate(),existEntity.getDate())){
            return existEntity;
        }else {
        nacBankRepository.save(entity);
        log.info(ANSI_GREEN+" Save currency NacBank"+ANSI_RESET);
        return entity;
        }
    }

    public List<NacBank> findAll(){
        return nacBankRepository.findAll();
    }
}
