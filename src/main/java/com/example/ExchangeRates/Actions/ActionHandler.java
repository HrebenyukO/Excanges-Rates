package com.example.ExchangeRates.Actions;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ActionHandler<T> {
    T performAction(String action, long chatId) throws TelegramApiException;
}
