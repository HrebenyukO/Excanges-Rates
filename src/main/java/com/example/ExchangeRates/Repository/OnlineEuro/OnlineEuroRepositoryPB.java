package com.example.ExchangeRates.Repository.OnlineEuro;

import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineEuroRepositoryPB extends JpaRepository<OnlineEuroPrivatBank,Long> {
}
