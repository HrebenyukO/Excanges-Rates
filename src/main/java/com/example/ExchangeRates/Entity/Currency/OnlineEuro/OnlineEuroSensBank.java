package com.example.ExchangeRates.Entity.Currency.OnlineEuro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OnlineEuroSensBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bank;
    private double onlinePurchaseDollar;
    private double onlineSaleDollar;
    private Date date;

    public OnlineEuroSensBank(String bank, double onlinePurchaseDollar, double onlineSaleDollar, Date date) {
        this.bank = bank;
        this.onlinePurchaseDollar = onlinePurchaseDollar;
        this.onlineSaleDollar = onlineSaleDollar;
        this.date = date;
    }
}
