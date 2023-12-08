package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineDollarRepository extends JpaRepository<OnlineDollar,Long> {
}
