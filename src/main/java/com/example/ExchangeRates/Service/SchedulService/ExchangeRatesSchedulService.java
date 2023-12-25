package com.example.ExchangeRates.Service.SchedulService;

import com.example.ExchangeRates.Entity.Currency.CashDollar;
import com.example.ExchangeRates.Entity.Currency.CashEuro;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro;
import com.example.ExchangeRates.Repository.CashDollarRepository;
import com.example.ExchangeRates.Repository.CashEuroRepository;
import com.example.ExchangeRates.Repository.OnlineDollarRepository;
import com.example.ExchangeRates.Repository.OnlineEuroRepository;
import com.example.ExchangeRates.Service.API.PrivatBankAPI;
import com.example.ExchangeRates.Mappers.CurrencyMappers;


import com.example.ExchangeRates.Service.TelegramBot;
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
   CurrencyMappers currencyMapper;
  @Autowired
    OnlineDollarRepository onlineDollarRepository;
  @Autowired
    OnlineEuroRepository onlineEuroRepository;
  @Autowired
    CashDollarRepository cashDollarRepository;
  @Autowired
    CashEuroRepository cashEuroRepository;

   private final String PRIVATBANK="PrivatBank";

    @Scheduled(cron = "0 0 * * * *")
    public void getExchangeRates(){
        // много кода доработать
        log.info("GET API");
        String jsonOnline= privatBankAPI.getOnlineExchangeRates();
        String jsonCash= privatBankAPI.getCashExchangeRates();

        CurrencyOnlineDTO onlineDTO=currencyMapper.JsonToCurrencyDTO(jsonOnline);
        CurrencyCashDTO cashDTO=currencyMapper.JsonToCurrencyCashDTO(jsonCash);

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
