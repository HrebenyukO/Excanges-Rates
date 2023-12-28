package com.example.ExchangeRates.Service.API;

import com.example.ExchangeRates.dto.NacBankDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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





    public NacBankDTO getExchangeRates() {
        RestTemplate restTemplate=new RestTemplate();
        String json = restTemplate.getForObject(NAC_BANK_API_URL_ExchangeRate, String.class);
        return mapToNacBankDTO(json);
    }

    private NacBankDTO mapToNacBankDTO(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        double dollar = 0.0;
        double euro = 0.0;
        Date date = null;

        try {
            JsonNode root = mapper.readTree(jsonString);
            for (JsonNode node : root) {
                String currencyCode = node.get("cc").asText();
                if (currencyCode.equals("USD")) {
                    dollar = node.get("rate").asDouble();
                } else if (currencyCode.equals("EUR")) {
                    euro = node.get("rate").asDouble();
                }
                String exchangeDate = node.get("exchangedate").asText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                java.util.Date parsedDate = dateFormat.parse(exchangeDate);
                date = new Date(parsedDate.getTime()); // Преобразование java.util.Date в java.sql.Date
            }
        } catch (JsonProcessingException | ParseException e) {
            throw new RuntimeException(e);
        }
        return new NacBankDTO(dollar, euro, date);
    }
}