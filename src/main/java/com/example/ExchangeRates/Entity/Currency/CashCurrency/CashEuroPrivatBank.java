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
public class CashEuroPrivatBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bank;
    private double cashPurchaseEuro;
    private double cashSaleEuro;
    private Date date;

    public CashEuroPrivatBank(String bank,
                                double cashPurchaseEuro,
                                double cashSaleEuro,
                                Date date) {
        this.bank = bank;
        this.cashPurchaseEuro = cashPurchaseEuro;
        this.cashSaleEuro = cashSaleEuro;
        this.date = date;
    }
}
