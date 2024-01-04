package com.example.ExchangeRates.Service.API;

import com.example.ExchangeRates.dto.NacBankDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

@Service
public class NacBankAPI {
    private static final String NAC_BANK_API_URL_ExchangeRate =
            "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final WebClient webClient;
    public NacBankAPI() {
        this.webClient = WebClient.create();
    }
    public Mono<String> getApiNacBank() {
        return webClient.get()
                .uri(NAC_BANK_API_URL_ExchangeRate)
                .retrieve()
                .bodyToMono(String.class);
    }
}