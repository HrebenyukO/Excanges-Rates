package com.example.ExchangeRates.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class ExchangeRates {

    public InlineKeyboardMarkup menuExchangeRates(){
        log.info("Work Exchanges service");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Щодене оповіщення");
        button.setCallbackData("Оповіщення по курсу валют");
        rowInLine.add(button);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup notifivation() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var buttonYES = new InlineKeyboardButton();
        buttonYES.setText("Ввімкнути");
        buttonYES.setCallbackData("start_notification");
        var buutonNo = new InlineKeyboardButton();
        buutonNo.setText("Ввимкнути");
        buutonNo.setCallbackData("stop_notification");
        rowInLine.add(buttonYES);
        rowInLine.add(buutonNo);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
}
