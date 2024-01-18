package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashEuro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashEuroRepository extends JpaRepository<CashEuro,Long> {
}
