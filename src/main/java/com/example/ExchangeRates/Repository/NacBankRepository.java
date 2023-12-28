package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.NacBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;


public interface NacBankRepository extends JpaRepository<NacBank,Long> {

   @Query(value = "INSERT INTO nac_bank_table (dollar, euro, date) VALUES (:id, :dollar, :euro, :date)", nativeQuery = true)
    NacBank save(@Param("dollar") Double dollar, @Param("euro") Double euro, @Param("date") Date date);

    @Query(value = "SELECT * FROM nac_bank_table", nativeQuery = true)
    List<NacBank> findAll();

}
