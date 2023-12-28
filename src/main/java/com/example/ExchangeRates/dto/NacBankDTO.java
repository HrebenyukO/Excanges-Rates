package com.example.ExchangeRates.dto;

import java.sql.Date;
import java.util.function.Function;

public record NacBankDTO (
        double dollar,
        double euro,
        Date date
){
}
