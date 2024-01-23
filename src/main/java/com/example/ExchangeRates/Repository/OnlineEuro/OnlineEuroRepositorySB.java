package com.example.ExchangeRates.Repository.OnlineEuro;

import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroSensBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineEuroRepositorySB extends JpaRepository<OnlineEuroSensBank,Long> {
    OnlineEuroSensBank findFirstByOrderByIdDesc();
}
