package com.example.ExchangeRates.Service.Currency;

import java.sql.Date;
import java.time.LocalDate;

public interface CurrencyService {

    default boolean isSameDate(Date date1, Date date2) {
        LocalDate localDate1 = date1.toLocalDate();
        LocalDate localDate2 = date2.toLocalDate();

        return localDate1.isEqual(localDate2);
    }
}
