package com.example.ExchangeRates.Service.API;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PrivatBankAPI {
    private static final String PRIVAT_BANK_API_URL_GOTIVKA =
            "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private static final String PRIVAT_BANK_API_URL_BEZGOTIVKA =
            "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";

    public String getOnlineExchangeRates() {
        RestTemplate restTemplate = new RestTemplate();
        String online= restTemplate.getForObject(PRIVAT_BANK_API_URL_BEZGOTIVKA, String.class);;
       return online;
    }

    public String getCashExchangeRates() {
        RestTemplate restTemplate = new RestTemplate();
        String  cash=restTemplate.getForObject(PRIVAT_BANK_API_URL_GOTIVKA, String.class);
        return cash;
    }


}
