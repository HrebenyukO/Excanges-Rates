package com.example.ExchangeRates.Entity.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OnlineEuroPrivatBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bank;
    private double onlinePurchaseEuro;
    private double onlineSaleEuro;
    private Date date;

    public OnlineEuroPrivatBank(String bank,
                                double onlinePurchaseEuro,
                                double onlineSaleEuro,
                                Date date) {
        this.bank = bank;
        this.onlinePurchaseEuro = onlinePurchaseEuro;
        this.onlineSaleEuro = onlineSaleEuro;
        this.date = date;
    }
}
