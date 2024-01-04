package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.OnlineDollarPrivatBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDollarRepositoryPB extends JpaRepository<OnlineDollarPrivatBank,Long> {
}
