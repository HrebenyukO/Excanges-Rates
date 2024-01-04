package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.OnlineEuroMonoBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineEuroRepositoryMB extends JpaRepository<OnlineEuroMonoBank,Long> {
}
