package com.example.ExchangeRates.Service.ButtonService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class ButtonService {

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

    public InlineKeyboardMarkup notifiсation() {
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

    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add("📊 Аналітика курсів валют");
        firstRow.add("💱 Криптовалюти");
        firstRow.add("📰 Новини МІНФІН");
        keyboardRows.add(firstRow);
        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("💵 КУРСИ ВАЛЮТ");
        keyboardRows.add(secondRow);
        keyboard.setKeyboard(keyboardRows);
        return keyboard;
    }
}
