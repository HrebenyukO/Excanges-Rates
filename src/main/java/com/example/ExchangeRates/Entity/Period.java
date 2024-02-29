package com.example.ExchangeRates.Entity;

import java.time.LocalDate;

public enum Period {
    DAY,
    TEN_DAYS,
    MONTH,
    QUARTER,
    YEAR;

    public Period plusYears(int years) {
        if (this == YEAR) {
            return this; // Годы не изменяются для периода YEAR
        }

        // В противном случае возвращаем следующий период
        return values()[(ordinal() + years) % values().length];
    }

    public LocalDate addTo(LocalDate date) {
        switch (this) {
            case DAY:
                return date.plusDays(1);
            case TEN_DAYS:
                return date.plusDays(10);
            case MONTH:
                return date.plusMonths(1);
            case QUARTER:
                return date.plusMonths(3);
            case YEAR:
                return date.plusYears(1);
            default:
                throw new UnsupportedOperationException("Unsupported period: " + this);
        }
    }
}
