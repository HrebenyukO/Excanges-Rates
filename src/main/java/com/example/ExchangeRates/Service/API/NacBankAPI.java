package com.example.ExchangeRates.Service.API;

import com.example.ExchangeRates.dto.NacBankDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

@Service
public class NacBankAPI {
    private static final String NAC_BANK_API_URL_ExchangeRate =
            "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    public String getApiNacBank() {
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate.getForObject(NAC_BANK_API_URL_ExchangeRate, String.class);
    }
}