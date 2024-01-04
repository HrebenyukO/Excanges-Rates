package com.example.ExchangeRates.Entity.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;
@Entity
@Data
public class OnlineEuroPrivatBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bank;
    private double onlinePurchaseEuro;
    private double onlineSaleEuro;
    private Date date;
}
