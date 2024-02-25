package com.example.ExchangeRates.Mappers.EntityMapper;

import com.example.ExchangeRates.Entity.Currency.ArсhiveCurrency.PrivatBankCurrencyArchive;
import com.example.ExchangeRates.Repository.ArchiveCurrency.ArchivePBRepository;
import com.example.ExchangeRates.Service.API.PrivatBankAPI;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class ArchiveCurrencyMapper {

    @Autowired
    private ArchivePBRepository archivePBRepository;
    private static final String BANK_NAME = "ПриватБанк";

    @Autowired
    private PrivatBankAPI privatBankAPI;
    private static final String API_URL_FORMAT = "https://api.privatbank.ua/p24api/exchange_rates?json&date=%s";
    public List<String> generateEntitiesForPeriod(LocalDate startDate, LocalDate endDate) {
        System.out.println("WORK RESPONS DATA");
        List<String> responses = new ArrayList<>();

        List<CompletableFuture<String>> futures = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            System.out.println(currentDate);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String apiUrl = String.format(API_URL_FORMAT, currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            CompletableFuture<String> future = WebClient.create()
                    .get()
                    .uri(apiUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .toFuture();

            futures.add(future);
            currentDate = currentDate.plusDays(1);
        }

        // Дождитесь завершения всех асинхронных запросов
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        // Извлеките результаты из CompletableFuture
        for (CompletableFuture<String> future : futures) {
            try {
                responses.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(responses.size());
        return responses;
    }

    private PrivatBankCurrencyArchive createEntity(String jsonString) {
        PrivatBankCurrencyArchive entity = new PrivatBankCurrencyArchive();
        entity.setBank(BANK_NAME);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(jsonString, new TypeReference<>() {});

            // Ищем нужные значения (доллар и евро) в responseMap и устанавливаем их в entity
            List<Map<String, Object>> exchangeRateList = (List<Map<String, Object>>) responseMap.get("exchangeRate");
            for (Map<String, Object> rate : exchangeRateList) {
                String currency = (String) rate.get("currency");
                Double saleRate = (Double) rate.get("saleRate");

                switch (currency) {
                    case "USD":
                        entity.setOnlineDollar(saleRate);
                        break;
                    case "EUR":
                        entity.setOnlineEuro(saleRate);
                        break;
                    // Добавьте другие валюты, если необходимо
                }
            }

            // Устанавливаем дату из responseMap в entity
            String dateString = (String) responseMap.get("date");

            // Форматируем строку даты в формат "dd.MM.yyyy"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(dateString, formatter);

            // Преобразуем LocalDate в java.sql.Date
            Date sqlDate = Date.valueOf(localDate);
            entity.setDate(sqlDate);

        } catch (IOException e) {
            // Обработка ошибок парсинга JSON
            e.printStackTrace();
        }
        System.out.println(entity);
        return entity;
    }



    public void saveEntitiesForDefaultPeriod() {
        System.out.println("WORK SAVE ENTITY 1");
        LocalDate startDate = LocalDate.of(2023, 12, 30);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        saveEntitiesForPeriod(startDate, endDate);
    }

    public void saveEntitiesForPeriod(LocalDate startDate, LocalDate endDate) {
        System.out.println("WORK SAVE ENTITY 2");
        List<String> responses = generateEntitiesForPeriod(startDate, endDate);
        System.out.println(responses.size());
        for (String response : responses) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            PrivatBankCurrencyArchive entity = createEntity(response);
            archivePBRepository.save(entity);
        }
    }

}