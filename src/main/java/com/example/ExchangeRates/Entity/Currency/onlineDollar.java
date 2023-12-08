package com.example.ExchangeRates.Entity.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class onlineDollar {
    @Id
    private int id;
    private double onlinePurchase;
    private double onlineSale;
}
