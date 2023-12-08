package com.example.ExchangeRates.Service.MapperService;

import com.example.ExchangeRates.Entity.Currency.CashDollar;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro;
import com.example.ExchangeRates.Repository.CashDollarRepository;
import com.example.ExchangeRates.Repository.OnlineDollarRepository;
import com.example.ExchangeRates.Repository.OnlineEuroRepository;
import com.example.ExchangeRates.dto.Currency;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeService {
    @Autowired
    OnlineDollarRepository onlineDollarRepository;
    @Autowired
    OnlineEuroRepository onlineEuroRepository;
    public Currency mapJsonToCurrencyDTO(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Currency currencyDTO = new Currency();
        currencyDTO.setBank("PrivatBank");

        try {
            List<Map<String, String>> currencyList = mapper.readValue(json, new TypeReference<List<Map<String, String>>>() {});
            for (Map<String, String> currencyMap : currencyList) {
                if (currencyMap.containsKey("ccy") && currencyMap.containsKey("buy") && currencyMap.containsKey("sale")) {
                    String currencyType = currencyMap.get("ccy");
                    if (currencyType.equals("USD")) {
                        currencyDTO.setOnlineDollarPurchase(Double.parseDouble(currencyMap.get("buy")));
                        currencyDTO.setOnlineDollarSales(Double.parseDouble(currencyMap.get("sale")));
                    } else if (currencyType.equals("EUR")) {
                        currencyDTO.setOnlineEuroPurchase(Double.parseDouble(currencyMap.get("buy")));
                        currencyDTO.setOnlineEuroSales(Double.parseDouble(currencyMap.get("sale")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencyDTO;
    }

    public void saveCurrencyToOnlineDollar(Currency currency) {
        OnlineDollar onlineDollar=new OnlineDollar();
        onlineDollar.setBank(currency.getBank());
        onlineDollar.setDate(new Date(System.currentTimeMillis()));
        onlineDollar.setOnlinePurchaseDollar(currency.getOnlineDollarPurchase());
        onlineDollar.setOnlineSaleDollar(currency.getOnlineDollarSales());
        onlineDollarRepository.save(onlineDollar);
    }
    public void saveCurrencyToOnlineEuro(Currency currency) {
        OnlineEuro onlineEuro=new OnlineEuro();
        onlineEuro.setBank(currency.getBank());
        onlineEuro.setDate(new Date(System.currentTimeMillis()));
        onlineEuro.setOnlinePurchaseEuro(currency.getOnlineEuroPurchase());
        onlineEuro.setOnlineSaleEuro(currency.getOnlineEuroSales());
        onlineEuroRepository.save(onlineEuro);
    }
}


