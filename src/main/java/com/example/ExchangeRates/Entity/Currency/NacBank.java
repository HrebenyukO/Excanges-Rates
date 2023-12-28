package com.example.ExchangeRates.Entity.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class NacBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double dollar;
    private double euro;
    private Date date;

    public NacBank(double dollar,double euro,Date date){
        this.dollar=dollar;
        this.euro=euro;
        this.date=date;
    }
}
