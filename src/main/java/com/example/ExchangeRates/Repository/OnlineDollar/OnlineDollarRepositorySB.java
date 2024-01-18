package com.example.ExchangeRates.Repository.OnlineDollar;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarSensBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDollarRepositorySB extends JpaRepository<OnlineDollarSensBank,Long> {
}
