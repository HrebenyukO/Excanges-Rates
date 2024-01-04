/*package com.example.ExchangeRates.Mappers.EntityMapper;

import com.example.ExchangeRates.Entity.Currency.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroMonoBank;
import com.example.ExchangeRates.Service.Converter.MonoBankConverter;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.function.Function;

@Component
public class EntityMapper<T, R> implements Function<T, R> {
    private final Function<T, CurrencyOnlineDTO> converter;
    private final Function<CurrencyOnlineDTO, String> bankNameSupplier;

    public EntityMapper(Function<T, CurrencyOnlineDTO> converter,
                        Function<CurrencyOnlineDTO, String> bankNameSupplier) {
        this.converter = converter;
        this.bankNameSupplier = bankNameSupplier;
    }

    @Override
    public R apply(T input) {
        CurrencyOnlineDTO onlineDTO = converter.apply(input);

        R entity = createEntity();
        String bankName = bankNameSupplier.apply(onlineDTO);

      //  setEntityDetails(entity, bankName, onlineDTO);
        return entity;
    }

    protected R createEntity() {
        // Create your specific entity here, this method should be implemented in subclasses
        return null;
    }

   *//* protected void setEntityDetails(R entity, String bankName, CurrencyOnlineDTO onlineDTO) {
        // Set specific entity details
        if (entity instanceof YourEntityType) {
            YourEntityType specificEntity = (YourEntityType) entity;
            specificEntity.setBank(bankName);
            specificEntity.setOnlinePurchaseDollar(onlineDTO.getOnlineDollarPurchase());
            specificEntity.setOnlineSaleDollar(onlineDTO.getOnlineDollarSales());
            specificEntity.setDate(new Date(System.currentTimeMillis()));
        }
    }*//*

}*/
