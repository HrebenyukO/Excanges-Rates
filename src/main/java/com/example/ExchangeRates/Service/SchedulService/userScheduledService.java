package com.example.ExchangeRates.Service.SchedulService;

import com.example.ExchangeRates.Service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class userScheduledService {
    @Autowired
TelegramBot telegramBot;
  /*  @Scheduled(cron = "* * 0 * * *")
    public void shedul(){
        telegramBot.sendMessage(497569697,"Shedullll");
    }*/
}
