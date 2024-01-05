package com.example.ExchangeRates.dto;

import java.sql.Date;


public record NacBankDTO (
        double dollar,
        double euro,
        Date date
){
}
