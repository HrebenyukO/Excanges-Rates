package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.CashDollar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashDollarRepository extends JpaRepository<CashDollar,Long> {
}
