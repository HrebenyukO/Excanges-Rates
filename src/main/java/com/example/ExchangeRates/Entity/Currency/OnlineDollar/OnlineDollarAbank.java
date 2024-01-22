package com.example.ExchangeRates.Entity.Currency.OnlineDollar;

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
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OnlineDollarAbank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bank;
    private double onlinePurchaseDollar;
    private double onlineSaleDollar;
    private Date date;
    public OnlineDollarAbank(String bank,
                             double onlinePurchaseDollar,
                             double onlineSaleDollar,
                             Date date) {
        this.bank = bank;
        this.onlinePurchaseDollar = onlinePurchaseDollar;
        this.onlineSaleDollar = onlineSaleDollar;
        this.date = date;
    }

}
