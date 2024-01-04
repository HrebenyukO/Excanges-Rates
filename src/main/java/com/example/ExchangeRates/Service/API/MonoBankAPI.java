package com.example.ExchangeRates.Service.API;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class MonoBankAPI {
   /* public static final String MONO_BANK_API_URL_ExchangeRate =
            "https://api.monobank.ua/bank/currency";
    public List<MonoBankCurrency> getApiMonobank() {
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<MonoBankCurrency[]> response = restTemplate.getForEntity(MONO_BANK_API_URL_ExchangeRate, MonoBankCurrency[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(response.getBody());
        } else {
            return Collections.emptyList();
        }
    }*/
}
