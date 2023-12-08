package com.example.ExchangeRates.Service.SchedulService;

import com.example.ExchangeRates.Service.API.PrivatBankAPI;
import com.example.ExchangeRates.Service.MapperService.ExchangeService;
import com.example.ExchangeRates.dto.Currency;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ExchangeRatesSchedulService {
    @Autowired
    ExchangeService exchangeService;
    @Autowired
    PrivatBankAPI privatBankAPI;

    @Scheduled(cron = "0 * * * * *")
    public void getExchangeRates(){
        log.debug("GET API");
        String json= privatBankAPI.getExchangeRates();
            Currency currency=exchangeService.mapJsonToCurrencyDTO(json);
            exchangeService.saveCurrencyToOnlineDollar(currency);
            exchangeService.saveCurrencyToOnlineEuro(currency);
    }


}
