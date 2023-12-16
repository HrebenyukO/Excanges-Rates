package com.example.ExchangeRates.dto;


public record CurrencyOnlineDTO(
        double onlineDollarPurchase,
        double onlineDollarSales,
        double onlineEuroPurchase,
        double onlineEuroSales
) {
}
