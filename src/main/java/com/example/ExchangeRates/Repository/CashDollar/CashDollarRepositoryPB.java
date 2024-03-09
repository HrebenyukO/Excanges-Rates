package com.example.ExchangeRates.Repository.CashDollar;

import com.example.ExchangeRates.Entity.Currency.CashCurrency.CashDollarPrivatBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CashDollarRepositoryPB extends JpaRepository<CashDollarPrivatBank,Long> {
    CashDollarPrivatBank findFirstByOrderByIdDesc();

}
