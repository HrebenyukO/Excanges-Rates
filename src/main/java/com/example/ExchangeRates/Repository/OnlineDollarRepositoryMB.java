package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.OnlineDollarMonobank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDollarRepositoryMB extends JpaRepository<OnlineDollarMonobank,Long> {
}
