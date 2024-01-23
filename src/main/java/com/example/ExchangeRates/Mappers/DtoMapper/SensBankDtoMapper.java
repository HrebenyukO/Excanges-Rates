package com.example.ExchangeRates.Mappers.DtoMapper;

import com.example.ExchangeRates.Service.API.SensBankApi;
import com.example.ExchangeRates.Service.API.aBankAPI;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SensBankDtoMapper {
    private final SensBankApi sensBankApi;

    @Autowired
    public SensBankDtoMapper(SensBankApi sensBankApi) {
        this.sensBankApi = sensBankApi;
    }

    public CurrencyOnlineDTO parseCurrencyOnline() {
        double onlineDollarPurchase = 0.0;
        double onlineDollarSales = 0.0;
        double onlineEuroPurchase = 0.0;
        double onlineEuroSales = 0.0;

        try {
            var document = Jsoup.connect("https://sensebank.ua/currency-exchange").get();

            // Ищем блок с USD / UAH
            Element usdBlock = document.select(".exchange-rate-tabs__item:has(h2:contains(USD / UAH))").first();

            if (usdBlock != null) {
                // Ищем элементы покупки и продажи для доллара
                Element purchaseDollarElement = usdBlock.select(".exchange-rate-tabs__info-item:has(.exchange-rate-tabs__info-label.text--small:containsOwn(Купівля)) .exchange-rate-tabs__info-value.h4").first();
                Element salesDollarElement = usdBlock.select(".exchange-rate-tabs__info-item:has(.exchange-rate-tabs__info-label.text--small:containsOwn(Продаж)) .exchange-rate-tabs__info-value.h4").first();

                if (purchaseDollarElement != null && salesDollarElement != null) {
                    // Получаем значения покупки и продажи для доллара
                    onlineDollarPurchase = parseCurrencyValue(purchaseDollarElement.text());
                    onlineDollarSales = parseCurrencyValue(salesDollarElement.text());
                }
            }

            // Ищем блок с EUR / UAH
            Element euroBlock = document.select(".exchange-rate-tabs__item:has(h2:contains(EUR / UAH))").first();

            if (euroBlock != null) {
                // Ищем элементы покупки и продажи для евро
                Element purchaseEuroElement = euroBlock.select(".exchange-rate-tabs__info-item:has(.exchange-rate-tabs__info-label.text--small:containsOwn(Купівля)) .exchange-rate-tabs__info-value.h4").first();
                Element salesEuroElement = euroBlock.select(".exchange-rate-tabs__info-item:has(.exchange-rate-tabs__info-label.text--small:containsOwn(Продаж)) .exchange-rate-tabs__info-value.h4").first();

                if (purchaseEuroElement != null && salesEuroElement != null) {
                    // Получаем значения покупки и продажи для евро
                    onlineEuroPurchase = parseCurrencyValue(purchaseEuroElement.text());
                    onlineEuroSales = parseCurrencyValue(salesEuroElement.text());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Создаем объект CurrencyOnlineDTO и возвращаем его
        return new CurrencyOnlineDTO(onlineDollarPurchase, onlineDollarSales, onlineEuroPurchase, onlineEuroSales);
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
