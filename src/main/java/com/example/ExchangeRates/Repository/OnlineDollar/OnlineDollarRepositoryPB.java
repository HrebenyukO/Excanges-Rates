package com.example.ExchangeRates.Repository.OnlineDollar;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDollarRepositoryPB extends JpaRepository<OnlineDollarPrivatBank,Long> {
}
