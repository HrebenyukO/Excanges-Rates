package com.example.ExchangeRates.Service.SchedulService;

import com.example.ExchangeRates.Entity.Currency.*;
import com.example.ExchangeRates.Mappers.NacBankMapper;
import com.example.ExchangeRates.Repository.*;
import com.example.ExchangeRates.Service.API.NacBankAPI;
import com.example.ExchangeRates.Service.API.PrivatBankAPI;
import com.example.ExchangeRates.Mappers.CurrencyMappers;
import com.example.ExchangeRates.dto.CurrencyCashDTO;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExchangeRatesSchedulService {
    @Autowired
    PrivatBankAPI privatBankAPI;
    @Autowired
    NacBankAPI nacBankAPI;
    @Autowired
   CurrencyMappers currencyMapper;
    @Autowired
    OnlineDollarRepository onlineDollarRepository;
    @Autowired
    OnlineEuroRepository onlineEuroRepository;
    @Autowired
    CashDollarRepository cashDollarRepository;
    @Autowired
    CashEuroRepository cashEuroRepository;

    @Autowired
    NacBankRepository nacBankRepository;
    @Autowired
    NacBankMapper nacBankMapper;
   private final String PRIVATBANK="PrivatBank";

    @Scheduled(cron = "0 2 * * * *")
    public void saveActualExchangeRates(){
        // много кода доработать
        log.info("GET && SAVE API");
        String jsonOnlinePB= privatBankAPI.getOnlineExchangeRates();
        String jsonCashPB= privatBankAPI.getCashExchangeRates();

        var dto=nacBankAPI.getExchangeRates();
        NacBank nacBank=nacBankMapper.apply(dto);
        nacBankRepository.save(nacBank);

        CurrencyOnlineDTO onlineDTO=currencyMapper.JsonToCurrencyDtoPB(jsonOnlinePB);
        CurrencyCashDTO cashDTO=currencyMapper.JsonToCurrencyCashDtoPB(jsonCashPB);

        OnlineDollar onlineDollar=currencyMapper.dtoToOnlineDollar(onlineDTO,PRIVATBANK);
        onlineDollarRepository.save(onlineDollar);
        OnlineEuro onlineEuro=currencyMapper.dtoToOnlineEuro(onlineDTO,PRIVATBANK);
        onlineEuroRepository.save(onlineEuro);

        CashDollar cashDollar=currencyMapper.dtoToCashDollar(cashDTO,PRIVATBANK);
        cashDollarRepository.save(cashDollar);
        CashEuro cashEuro=currencyMapper.dtoToCashEuro(cashDTO,PRIVATBANK);
        cashEuroRepository.save(cashEuro);
        log.info("Save currency");
    }


}
