package com.example.ExchangeRates.Repository.OnlineDollar;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarAbank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDollarRepositoryAB extends JpaRepository<OnlineDollarAbank,Long> {
    OnlineDollarAbank findFirstByOrderByIdDesc();
}
