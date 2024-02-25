package com.example.ExchangeRates.Repository.ArchiveCurrency;

import com.example.ExchangeRates.Entity.Currency.ArсhiveCurrency.PrivatBankCurrencyArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivePBRepository extends JpaRepository <PrivatBankCurrencyArchive,Long> {
}
