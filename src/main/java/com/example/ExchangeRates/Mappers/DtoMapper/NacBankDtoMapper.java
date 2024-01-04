package com.example.ExchangeRates.Mappers.DtoMapper;

import com.example.ExchangeRates.Service.API.NacBankAPI;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import com.example.ExchangeRates.dto.NacBankDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class NacBankDtoMapper {

    @Autowired
    private NacBankAPI nacBankAPI;
    public NacBankDTO mapToNacBankDTO() {
        String jsonString = nacBankAPI.getApiNacBank().block();
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
                date = new Date(parsedDate.getTime());
            }
        } catch (JsonProcessingException | ParseException e) {
            throw new RuntimeException(e);
        }
        return new NacBankDTO(dollar, euro, date);
    }

}
