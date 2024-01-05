package com.example.ExchangeRates.Mappers.DtoMapper;

import com.example.ExchangeRates.Service.API.MonoBankAPI;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonoBankDtoMapper {

    @Autowired
    MonoBankAPI monoBankAPI;
    public CurrencyOnlineDTO mapToCurrencyOnlineDto() {
        String jsonString = monoBankAPI.getApiMonoBank().block();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonString);

            double onlineDollarPurchase = 0.0;
            double onlineDollarSales = 0.0;
            double onlineEuroPurchase = 0.0;
            double onlineEuroSales = 0.0;

            // Проходим по каждому элементу списка API и сопоставляем соответствующие поля
            for (JsonNode node : root) {
                int currencyCodeA = node.get("currencyCodeA").asInt();
                int currencyCodeB = node.get("currencyCodeB").asInt();

                if (currencyCodeA == 840 && currencyCodeB == 980) {
                    onlineDollarPurchase = node.get("rateBuy").asDouble();
                    onlineDollarSales = node.get("rateSell").asDouble();
                } else if (currencyCodeA == 978 && currencyCodeB == 980) {
                    onlineEuroPurchase = node.get("rateBuy").asDouble();
                    onlineEuroSales = node.get("rateSell").asDouble();
                }
            }

            return new CurrencyOnlineDTO(onlineDollarPurchase, onlineDollarSales, onlineEuroPurchase, onlineEuroSales);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map JSON to CurrencyOnlineDTO: " + e.getMessage());
        }
    }
}
