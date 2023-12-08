package com.example.ExchangeRates.Service.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PrivatBankAPI {
    private static final String PRIVAT_BANK_API_URL_GOTIVKA =
            "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private static final String PRIVAT_BANK_API_URL_BEZGOTIVKA =
            "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";

    public String getExchangeRates() {
        RestTemplate restTemplate = new RestTemplate();
        String online= restTemplate.getForObject(PRIVAT_BANK_API_URL_BEZGOTIVKA, String.class);;
        String  gotivka=restTemplate.getForObject(PRIVAT_BANK_API_URL_GOTIVKA, String.class);
       return online;
    }


}
