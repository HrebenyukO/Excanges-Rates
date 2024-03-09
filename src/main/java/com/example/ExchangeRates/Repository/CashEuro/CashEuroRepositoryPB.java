package com.example.ExchangeRates.Repository.CashEuro;

import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashEuroPrivatBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashEuroRepositoryPB extends JpaRepository<CashEuroPrivatBank,Long> {
    CashEuroPrivatBank  findFirstByOrderByIdDesc();
}
