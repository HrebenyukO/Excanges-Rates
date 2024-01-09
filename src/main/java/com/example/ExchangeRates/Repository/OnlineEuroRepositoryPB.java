package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.OnlineEuroMonoBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroPrivatBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineEuroRepositoryPB extends JpaRepository<OnlineEuroPrivatBank,Long> {
}
