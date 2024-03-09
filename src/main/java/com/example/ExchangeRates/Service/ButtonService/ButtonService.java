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

    public InlineKeyboardMarkup analyzeBanksDollar(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var buttonPB = new InlineKeyboardButton();
        buttonPB.setText("ПриватБанк");
        buttonPB.setCallbackData("PrivatChart_$");
        var buttonMB=new InlineKeyboardButton();
        buttonMB.setText("Mонобанк");
        buttonMB.setCallbackData("MonobankChart_$");
        var  buttonAB=new InlineKeyboardButton("АБАНК");
        buttonAB.setCallbackData("AbankChart_$");
        rowInLine.add(buttonPB);
        rowInLine.add(buttonMB);
        rowInLine.add(buttonAB);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup analyzeBanksEuro(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var buttonPB = new InlineKeyboardButton();
        buttonPB.setText("ПриватБанк");
        buttonPB.setCallbackData("PrivatChart_€");
        var buttonMB=new InlineKeyboardButton();
        buttonMB.setText("Mонобанк");
        buttonMB.setCallbackData("MonobankChart_€");
        var  buttonAB=new InlineKeyboardButton("АБАНК");
        buttonAB.setCallbackData("AbankChart_€");
        rowInLine.add(buttonPB);
        rowInLine.add(buttonMB);
        rowInLine.add(buttonAB);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup privatbankAnalyseDollar(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Останні 10 днів");
        button.setCallbackData("chart_Analyse_10_days_PB");
        var button2=new InlineKeyboardButton();
        button2.setText("Місяць");
        button2.setCallbackData("chart_Analyse_month_PB");
        var button3=new InlineKeyboardButton();
        button3.setText("Квартал");
        button3.setCallbackData("chart_Analyse_kvartal");
        rowInLine.add(button);
        rowInLine.add(button2);
        rowInLine.add(button3);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup privatbankAnalyseEuro() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Останні 10 днів");
        button.setCallbackData("chart_Analyse_10_days_PB_€");
        var button2 = new InlineKeyboardButton();
        button2.setText("Місяць");
        button2.setCallbackData("chart_Analyse_month_PB_€");
        var button3 = new InlineKeyboardButton();
        button3.setText("Квартал");
        button3.setCallbackData("chart_Analyse_kvartal_PB_€");
        rowInLine.add(button);
        rowInLine.add(button2);
        rowInLine.add(button3);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup monoBankAnalyseDollar(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Останні 10 днів");
        button.setCallbackData("chart_Analyse_10_days_MB");
        var button2=new InlineKeyboardButton();
        button2.setText("Місяць");
        button2.setCallbackData("chart_Analyse_month_MB");
        var button3=new InlineKeyboardButton();
        button3.setText("Квартал");
        button3.setCallbackData("chart_Analyse_kvartal_MB");
        rowInLine.add(button);
        rowInLine.add(button2);
        rowInLine.add(button3);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup monoBankAnalyseEuro(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Останні 10 днів");
        button.setCallbackData("chart_Analyse_10_days_MB_€");
        var button2=new InlineKeyboardButton();
        button2.setText("Місяць");
        button2.setCallbackData("chart_Analyse_month_MB_€");
        var button3=new InlineKeyboardButton();
        button3.setText("Квартал");
        button3.setCallbackData("chart_Analyse_kvartal_MB_€");
        rowInLine.add(button);
        rowInLine.add(button2);
        rowInLine.add(button3);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup aBankAnalyseDollar(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Останні 10 днів");
        button.setCallbackData("chart_Analyse_10_days_AB");
        var button2=new InlineKeyboardButton();
        button2.setText("Місяць");
        button2.setCallbackData("chart_Analyse_month_AB");
        var button3=new InlineKeyboardButton();
        button3.setText("Квартал");
        button3.setCallbackData("chart_Analyse_kvartal");
        rowInLine.add(button);
        rowInLine.add(button2);
        rowInLine.add(button3);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup aBankAnalyseEuro(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Останні 10 днів");
        button.setCallbackData("chart_Analyse_10_days_AB_€");
        var button2=new InlineKeyboardButton();
        button2.setText("Місяць");
        button2.setCallbackData("chart_Analyse_month_AB_€");
        var button3=new InlineKeyboardButton();
        button3.setText("Квартал");
        button3.setCallbackData("chart_Analyse_kvartal_AB_€");
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
        firstRow.add("\uD83D\uDECE Повідомлення");
        firstRow.add("Пропозиції до розробника");
        keyboardRows.add(firstRow);
        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("💵 КУРСИ ВАЛЮТ");
        keyboardRows.add(secondRow);
        keyboard.setKeyboard(keyboardRows);
        return keyboard;
    }
    public InlineKeyboardMarkup notifiсationChainER() {
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
    public InlineKeyboardMarkup mainNotification() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var buttonYES = new InlineKeyboardButton();
        buttonYES.setText("\uD83D\uDCB9 Зміна курсу");
        buttonYES.setCallbackData("notification_main_change_ER");
        var buutonNo = new InlineKeyboardButton();
        buutonNo.setText("⌛ Погодинне");
        buutonNo.setCallbackData("notification_main_time_ER");
        rowInLine.add(buttonYES);
        rowInLine.add(buutonNo);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup menuAnalyze() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var buttonYES = new InlineKeyboardButton();
        buttonYES.setText("\uD83D\uDCB2");
        buttonYES.setCallbackData("analyze_main_dollar_menu");
        rowInLine.add(buttonYES);

        var buutonNo = new InlineKeyboardButton();
        buutonNo.setText(" € ");
        buutonNo.setCallbackData("analyze_main_EURO_menu");
        rowInLine.add(buutonNo);

        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);

        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup mainAnalyzeEuro() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var buttonYES = new InlineKeyboardButton();
        buttonYES.setText("\uD83D\uDCB9 до курсу НБУ");
        buttonYES.setCallbackData("analyze_main_ER_NBU_€");
        var buutonNo = new InlineKeyboardButton();
        buutonNo.setText("⌛ По рокам ");
        buutonNo.setCallbackData("chart_analyze_main_ER_years_€");
        rowInLine.add(buttonYES);
        rowInLine.add(buutonNo);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup mainAnalyzeDollar() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var buttonYES = new InlineKeyboardButton();
        buttonYES.setText("\uD83D\uDCB9 до курсу НБУ");
        buttonYES.setCallbackData("analyze_main_ER_NBU");
        var buutonNo = new InlineKeyboardButton();
        buutonNo.setText("⌛ По рокам ");
        buutonNo.setCallbackData("chart_analyze_main_ER_years");
        rowInLine.add(buttonYES);
        rowInLine.add(buutonNo);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup menuER() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var online = new InlineKeyboardButton();
        online.setText("\uD83C\uDF10 Онлайн");
        online.setCallbackData("chart_menu_ER_online");
        rowInLine.add(online);

        var gotivka = new InlineKeyboardButton();
        gotivka.setText("\uD83D\uDCB5 Наличные");
        gotivka.setCallbackData("chart_menu_ER_gotivka");
        rowInLine.add(gotivka);

        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);

        return inlineKeyboardMarkup;
    }

}
