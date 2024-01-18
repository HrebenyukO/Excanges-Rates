package com.example.ExchangeRates.Mappers.DtoMapper;

import com.example.ExchangeRates.Service.API.SensBankApi;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


@Component
public class SensBankDtoMapper {
    @Autowired
    SensBankApi sensBankApi;

    public CurrencyOnlineDTO parseCurrencyOnline() {
        String pageContent = sensBankApi.getCurrencyPage();
        double onlineDollarPurchase = parseValueFromPage(pageContent,
                "//*[@id=\"__layout\"]/div/div[2]/main/section/div[1]/section/div[2]/div/div[1]/ul/li[1]");
        double onlineDollarSales = parseValueFromPage(pageContent,
                "//*[@id=\"__layout\"]/div/div[2]/main/section/div[1]/section/div[2]/div/div[1]/ul/li[3]");
        double onlineEuroPurchase = parseValueFromPage(pageContent,
                "//*[@id=\"__layout\"]/div/div[2]/main/section/div[1]/section/div[2]/div/div[2]/ul/li[1]");
        double onlineEuroSales = parseValueFromPage(pageContent,
                "//*[@id=\"__layout\"]/div/div[2]/main/section/div[1]/section/div[2]/div/div[2]/ul/li[3]");

        return new CurrencyOnlineDTO(onlineDollarPurchase, onlineDollarSales,
                onlineEuroPurchase, onlineEuroSales);
    }

    private double parseValueFromPage(String pageContent, String xpath) {
        try {
            Document document = Jsoup.parse(pageContent);
            Elements elements = document.select(xpath);

            if (!elements.isEmpty()) {
                String valueStr = elements.first().text();
                return Double.parseDouble(valueStr.replaceAll("[^\\d.]+", ""));
            }
        } catch (Exception e) {
            // Обработка ошибок, если таковые произойдут
            e.printStackTrace();
        }

        return 0.0;
    }
}
