package com.example.ExchangeRates.Service.Currency;

import com.example.ExchangeRates.Entity.Currency.NacBank;
import com.example.ExchangeRates.Mappers.EntityMapper.NacBankEntityMapper;
import com.example.ExchangeRates.Repository.NacBankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NacBankCurrencyBeanService {
    @Autowired
    NacBankEntityMapper entityMapper;
    @Autowired
    NacBankRepository nacBankRepository;
    public NacBank create(){
        NacBank entity=entityMapper.mapToNacBank();
        nacBankRepository.save(entity);
        log.info("Save currency NacBank");
        return entity;
    }

    public List<NacBank> findAll(){
        return nacBankRepository.findAll();
    }
}
