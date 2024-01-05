package com.example.ExchangeRates.Mappers.DtoMapper;

import com.example.ExchangeRates.Service.API.PrivatBankAPI;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrivatBankDtoMapper {

    private final PrivatBankAPI privatBankAPI;
    public CurrencyOnlineDTO mapToCurrencyOnlineDto() {
        String jsonString = privatBankAPI.getApiPrivatBankOnline().block();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonString);
            // Предполагается, что структура JSON соответствует вашему ожиданию

            double buyEUR = root.get(0).get("buy").asDouble();
            double sellEUR = root.get(0).get("sale").asDouble();
            double buyUSD = root.get(1).get("buy").asDouble();
            double sellUSD = root.get(1).get("sale").asDouble();
            return new CurrencyOnlineDTO(buyUSD, sellUSD, buyEUR, sellEUR);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map JSON to CurrencyOnlineDTO: " + e.getMessage());
        }
    }
}
