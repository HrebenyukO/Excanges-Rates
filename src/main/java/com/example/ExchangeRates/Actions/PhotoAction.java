package com.example.ExchangeRates.Actions;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public interface PhotoAction extends Action {
    SendPhoto performWithPhoto(long chatId) throws TelegramApiException;
}