package com.example.ExchangeRates.Repository.OnlineEuro;


import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroUkrGazBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineEuroRepositoryUkrGazBank extends JpaRepository<OnlineEuroUkrGazBank,Long> {
    OnlineEuroUkrGazBank findFirstByOrderByIdDesc();
}
