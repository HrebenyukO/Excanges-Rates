package com.example.ExchangeRates.Entity.Currency.OnlineEuro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OnlineEuroSensBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bank;
    private double onlinePurchaseEuro;
    private double onlineSaleEuro;
    private Date date;

    public OnlineEuroSensBank(String bank,
                                double onlinePurchaseEuro,
                                double onlineSaleEuro,
                                Date date) {
        this.bank = bank;
        this.onlinePurchaseEuro = onlinePurchaseEuro;
        this.onlineSaleEuro = onlineSaleEuro;
        this.date = date;
    }
}
