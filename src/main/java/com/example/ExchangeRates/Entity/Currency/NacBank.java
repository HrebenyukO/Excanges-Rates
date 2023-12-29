package com.example.ExchangeRates.Entity.Currency;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "nac_bank")
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
