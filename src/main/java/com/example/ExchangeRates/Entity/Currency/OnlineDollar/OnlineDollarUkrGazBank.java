package com.example.ExchangeRates.Entity.Currency.OnlineDollar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OnlineDollarUkrGazBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bank;
    private double onlinePurchaseDollar;
    private double onlineSaleDollar;
    private Date date;
    public OnlineDollarUkrGazBank(String bank,
                             double onlinePurchaseDollar,
                             double onlineSaleDollar,
                             Date date) {
        this.bank = bank;
        this.onlinePurchaseDollar = onlinePurchaseDollar;
        this.onlineSaleDollar = onlineSaleDollar;
        this.date = date;
    }
}
