package com.example.ExchangeRates.Service.SendingService;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface TelegramMessage {
    SendMessage sendMessageWithKeyboard(long chatID, String textToSend, InlineKeyboardMarkup keyboardMarkup);
    SendMessage sendMessage(long chatID, String textToSend);
    SendMessage startCommandReceived(long chatID, String firstName);
    SendPhoto sendTableToTelegram(long chatId);
    SendPhoto sendChartToTelegram(byte [] array,long chatID);
}
