package com.example.ExchangeRates.Repository.OnlineDollar;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarUkrGazBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDollarRepositoryUkrGazBank extends JpaRepository<OnlineDollarUkrGazBank,Long> {
    OnlineDollarUkrGazBank findFirstByOrderByIdDesc();
}
