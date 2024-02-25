package com.example.ExchangeRates.Entity.Currency.ArсhiveCurrency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;


@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class PrivatBankCurrencyArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bank;
    private double onlineDollar;
    private double onlineEuro;
    private Date date;

    public PrivatBankCurrencyArchive(String bank, double onlineDollar, double onlineEuro, Date date) {
        this.bank = bank;
        this.onlineDollar = onlineDollar;
        this.onlineEuro = onlineEuro;
        this.date = date;
    }
}
