package com.example.ExchangeRates.Repository.OnlineDollar;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDollarRepositoryMB extends JpaRepository<OnlineDollarMonobank,Long> {
    OnlineDollarMonobank findFirstByOrderByIdDesc();
}
