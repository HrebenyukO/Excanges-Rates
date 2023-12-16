package com.example.ExchangeRates.dto;

import java.util.function.Consumer;

public record CurrencyCashDTO(
        double cashDollarPurchase,
        double cashDollarSales,
        double cashEuroPurchase,
        double cashEuroSales
) {
}
