package com.example.ExchangeRates.Actions;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@FunctionalInterface
public interface ActionPicture {
    SendPhoto performPicture(long chatId) throws TelegramApiException;
}