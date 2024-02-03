package com.example.ExchangeRates.Mappers.DtoMapper;


import com.example.ExchangeRates.Service.API.ukrGazBankAPI;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.management.Query.div;

@Component
public class UkrGazBankDtoMapper {
    @Autowired
      private ukrGazBankAPI ukrgazBankApi;

    public CurrencyOnlineDTO parseCurrencyOnline() {
        String htmlPage = ukrgazBankApi.getCurrencyPage();

        try {
            // Парсим HTML с использованием Jsoup
            Document doc = Jsoup.parse(htmlPage);

            // Ищем нужные элементы на странице с учетом новой структуры
            Element cardRatesHeader = doc.select("h3:contains(Карточные курсы)").first();
            Element usdRow = cardRatesHeader.nextElementSibling().select("tr:has(td:contains(USD))").first();
            Element eurRow = cardRatesHeader.nextElementSibling().select("tr:has(td:contains(EUR))").first();

            // Получаем текстовые значения и парсим их в double
            double onlineDollarPurchase = parseCurrencyValue(usdRow.select("td:eq(1)").text());
            double onlineDollarSales = parseCurrencyValue(usdRow.select("td:eq(2)").text());
            double onlineEuroPurchase = parseCurrencyValue(eurRow.select("td:eq(1)").text());
            double onlineEuroSales = parseCurrencyValue(eurRow.select("td:eq(2)").text());

            // Создаем и возвращаем объект CurrencyOnlineDTO
            return new CurrencyOnlineDTO(onlineDollarPurchase, onlineDollarSales, onlineEuroPurchase, onlineEuroSales);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private double parseCurrencyValue(String input) {
        // Используем регулярное выражение для извлечения числа с плавающей запятой
        String numberRegex = "[+-]?\\d*\\.?\\d+";
        Pattern pattern = Pattern.compile(numberRegex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        } else {
            return 0.0;
        }
    }
}