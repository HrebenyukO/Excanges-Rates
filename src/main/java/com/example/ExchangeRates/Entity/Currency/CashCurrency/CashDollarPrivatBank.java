package com.example.ExchangeRates.Entity.Currency.CashCurrency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@NoArgsConstructor
@Data
public class CashDollarPrivatBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bank;
    private double cashPurchaseDollar;
    private double cashSaleDollar;
    private Date date;

    public CashDollarPrivatBank(String bank,
                                double cashPurchaseDollar,
                                double cashSaleDollar,
                                Date date) {
        this.bank = bank;
        this.cashPurchaseDollar = cashPurchaseDollar;
        this.cashSaleDollar = cashSaleDollar;
        this.date = date;
    }
}
