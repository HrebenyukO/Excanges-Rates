package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Entity.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartService<T> {
    @Autowired
    private MonoBankCurrencyChartCreator<T> monoBankCurrencyChartCreator;
    @Autowired
    PrivatBankCurrencyChartCreator<T> privatBankCurrencyChartCreator;
    @Autowired
    AbankCurrencyChart<T> abankCurrencyChartCreator;

    public byte[] buildChart(Period period, Class<T> entityClass) {
        if (entityClass.equals(OnlineDollarMonobank.class) ||
                entityClass.equals(OnlineEuroMonoBank.class)) {
            return monoBankCurrencyChartCreator.convertImageToByteArray(period, entityClass);
        } else if(entityClass.equals(OnlineDollarPrivatBank.class)||
        entityClass.equals(OnlineEuroPrivatBank.class)){
            return privatBankCurrencyChartCreator.convertImageToByteArray(period,entityClass);
        } else if(entityClass.equals(OnlineDollarAbank.class)||
                entityClass.equals(OnlineEuroAbank.class)){
            return abankCurrencyChartCreator.convertImageToByteArray(period,entityClass);}
        else return null;
    }
}