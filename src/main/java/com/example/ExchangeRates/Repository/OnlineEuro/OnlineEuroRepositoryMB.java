package com.example.ExchangeRates.Repository.OnlineEuro;

import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineEuroRepositoryMB extends JpaRepository<OnlineEuroMonoBank,Long> {
    OnlineEuroMonoBank findFirstByOrderByIdDesc();
}
