package com.example.ExchangeRates.Entity.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class cachDollar {
    @Id
    private int id;
    private double cashPurchase;
    private double cashSale;
}
