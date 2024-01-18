package com.example.ExchangeRates.Config;

import com.example.ExchangeRates.Service.Currency.CurrencyFacade;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Data
@Configuration
@EnableScheduling
@PropertySource("application.properties")
public class BotConfig {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;

    @Autowired
    private CurrencyFacade currencyFacade;

    @Scheduled(cron = "0 * * * * *") // Расписание запуска метода
    public void saveActualExchangeRates() {
        currencyFacade.getAndSaveActualCurrency();
    }
}
