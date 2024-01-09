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
        var button2=new InlineKeyboardButton();
        button2.setText("Оповіщення про зміни курсу");
        button2.setCallbackData("Оповіщення про зміни курсу");
        rowInLine.add(button);
        rowInLine.add(button2);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup analyseExchangeRates(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var buttonPB = new InlineKeyboardButton();
        buttonPB.setText("ПриватБанк");
        buttonPB.setCallbackData("PrivatChart");
        var buttonMB=new InlineKeyboardButton();
        buttonMB.setText("Mонобанк");
        buttonMB.setCallbackData("MonobankChart");
        rowInLine.add(buttonPB);
        rowInLine.add(buttonMB);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup privatbankAnalyse(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Останні 10 днів");
        button.setCallbackData("Analyse_10_days");
        var button2=new InlineKeyboardButton();
        button2.setText("Місяць");
        button2.setCallbackData("Analyse_month_PB");
        var button3=new InlineKeyboardButton();
        button3.setText("Квартал");
        button3.setCallbackData("Analyse_kvartal");
        rowInLine.add(button);
        rowInLine.add(button2);
        rowInLine.add(button3);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup monoBankAnalyse(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Останні 10 днів");
        button.setCallbackData("Analyse_10_days_MB");
        var button2=new InlineKeyboardButton();
        button2.setText("Місяць");
        button2.setCallbackData("Analyse_month_MB");
        var button3=new InlineKeyboardButton();
        button3.setText("Квартал");
        button3.setCallbackData("Analyse_kvartal");
        rowInLine.add(button);
        rowInLine.add(button2);
        rowInLine.add(button3);
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

    public InlineKeyboardMarkup notifiсation2() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var buttonYES = new InlineKeyboardButton();
        buttonYES.setText("Ввімкнути");
        buttonYES.setCallbackData("start_notification_chain");
        var buutonNo = new InlineKeyboardButton();
        buutonNo.setText("Ввимкнути");
        buutonNo.setCallbackData("stop_notification_chain");
        rowInLine.add(buttonYES);
        rowInLine.add(buutonNo);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
}
