package com.example.ExchangeRates.Entity.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;


@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OnlineDollarPrivatBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bank;
    private double onlinePurchaseDollar;
    private double onlineSaleDollar;
    private Date date;

    public OnlineDollarPrivatBank(String bank, double onlinePurchaseDollar, double onlineSaleDollar, Date date) {
        this.bank = bank;
        this.onlinePurchaseDollar = onlinePurchaseDollar;
        this.onlineSaleDollar = onlineSaleDollar;
        this.date = date;
    }
}
