package com.example.ExchangeRates.dto;

import lombok.Data;

@Data
public class Currency {

    private String bank;
    private double onlineDollarPurchase;
    private double onlineDollarSales;
    private double cashDollarPurchase;
    private double cashDollarSales;

    private double onlineEuroPurchase;
    private double onlineEuroSales;
    private double cashEuroPurchase;
    private double cashEuroSales;

}
