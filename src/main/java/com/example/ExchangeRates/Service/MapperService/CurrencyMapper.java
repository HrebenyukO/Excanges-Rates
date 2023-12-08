package com.example.ExchangeRates.Service.MapperService;

import com.example.ExchangeRates.dto.Currency;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class CurrencyMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Currency> mapJsonToCurrencies(String json) throws IOException {
        return objectMapper.readValue(json, new TypeReference<List<Currency>>() {});
    }
}