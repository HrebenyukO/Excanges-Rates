package com.example.ExchangeRates.Actions;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@FunctionalInterface
public interface Action {
    BotApiMethod<?> perform(long chatId) throws TelegramApiException;
}