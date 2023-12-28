package com.example.ExchangeRates.Mappers;

import com.example.ExchangeRates.Entity.Currency.*;
import com.example.ExchangeRates.dto.CurrencyCashDTO;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CurrencyMappers {
    public CurrencyOnlineDTO JsonToCurrencyDtoPB(String json) {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject euroObject = jsonArray.getJSONObject(0);
        JSONObject dollarObject = jsonArray.getJSONObject(1);
        return new CurrencyOnlineDTO(
                Double.parseDouble(dollarObject.getString("buy")),
                Double.parseDouble(dollarObject.getString("sale")),
                Double.parseDouble(euroObject.getString("buy")),
                Double.parseDouble(euroObject.getString("sale"))
        );
    }
    public CurrencyCashDTO JsonToCurrencyCashDtoPB(String json) {
        JSONArray jsonArray = new JSONArray(json);

        JSONObject euroObject = jsonArray.getJSONObject(0);
        JSONObject dollarObject = jsonArray.getJSONObject(1);
        return new CurrencyCashDTO(
                Double.parseDouble(dollarObject.getString("buy")),
                Double.parseDouble(dollarObject.getString("sale")),
                Double.parseDouble(euroObject.getString("buy")),
                Double.parseDouble(euroObject.getString("sale"))
        );
    }



    public CashDollar dtoToCashDollar(CurrencyCashDTO dto, String bank ){
    CashDollar entity=new CashDollar();
    entity.setBank(bank);
    entity.setCashSaleDollar(dto.cashDollarSales());
    entity.setCashPurchaseDollar(dto.cashDollarPurchase());
    entity.setDate(new Date(System.currentTimeMillis()));
    return entity;
    }
    public CashEuro dtoToCashEuro(CurrencyCashDTO dto, String bank ){
        CashEuro entity=new CashEuro();
        entity.setBank(bank);
        entity.setCashSaleEuro(dto.cashEuroSales());
        entity.setCashPurchaseEuro(dto.cashEuroPurchase());
        entity.setDate(new Date(System.currentTimeMillis()));
        return entity;
    }
    public OnlineDollar dtoToOnlineDollar(CurrencyOnlineDTO dto, String bank ){
        OnlineDollar entity=new OnlineDollar();
        entity.setBank(bank);
        entity.setOnlineSaleDollar(dto.onlineDollarSales());
        entity.setOnlinePurchaseDollar(dto.onlineDollarPurchase());
        entity.setDate(new Date(System.currentTimeMillis()));
        return entity;
    }
    public OnlineEuro dtoToOnlineEuro(CurrencyOnlineDTO dto, String bank ){
        OnlineEuro entity=new OnlineEuro();
        entity.setBank(bank);
        entity.setOnlineSaleEuro(dto.onlineEuroSales());
        entity.setOnlinePurchaseEuro(dto.onlineEuroPurchase());
        entity.setDate(new Date(System.currentTimeMillis()));
        return entity;
    }

}
