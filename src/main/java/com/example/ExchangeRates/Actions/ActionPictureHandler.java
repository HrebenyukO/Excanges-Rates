package com.example.ExchangeRates.Actions;

import com.example.ExchangeRates.Entity.Currency.ArсhiveCurrency.PrivatBankCurrencyArchive;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Entity.Period;
import com.example.ExchangeRates.Service.Charts.ChartService;
import com.example.ExchangeRates.Service.SendTable.CreateTable;
import com.example.ExchangeRates.Service.SendingService.TelegramMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
@Component
public class ActionPictureHandler implements ActionHandler<SendPhoto> {

    @Autowired
    private TelegramMessage telegramMessage;

    @Autowired
    private ChartService chartService;
    @Autowired
    CreateTable createTable;
    private  byte[] chart;
   private final Map<String, ActionPicture> actions = new HashMap<>();
    public ActionPictureHandler()
    {
        actions.put("chart_menu_ER_online", chatId -> {
            chart = createTable.createCurrencyTableBytes();
            return telegramMessage.sendChartToTelegram(chart,chatId);
        });

        // АНАЛІТИКА У ПОРІВНЯННІ ДО НБУ
        //Аналітика $ ПриватБанка
        actions.put("chart_Analyse_10_days_PB", chatId -> {
            chart = chartService.buildChartWithNBU(Period.TEN_DAYS, OnlineDollarPrivatBank.class);
            return telegramMessage.sendChartToTelegram( chart,chatId );
        });

        actions.put("chart_Analyse_month_PB", chatId -> {
            chart = chartService.buildChartWithNBU(Period.MONTH, OnlineDollarPrivatBank.class);
            return telegramMessage.sendChartToTelegram( chart,chatId );
        });

        actions.put("chart_Analyse_kvartal", chatId -> {
            chart = chartService.buildChartWithNBU(Period.QUARTER, OnlineDollarPrivatBank.class);
            return telegramMessage.sendChartToTelegram( chart,chatId );
        });

        //Аналітика € ПриватБанка
        actions.put("chart_Analyse_10_days_PB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.TEN_DAYS, OnlineEuroPrivatBank.class);
            return  telegramMessage.sendChartToTelegram(chart, chatId);
        });

        actions.put("chart_Analyse_month_PB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.MONTH, OnlineEuroPrivatBank.class);
            return  telegramMessage.sendChartToTelegram(chart, chatId);
        });

        actions.put("chart_Analyse_kvartal_PB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.QUARTER, OnlineEuroPrivatBank.class);
            return  telegramMessage.sendChartToTelegram(chart, chatId);
        });


          // Аналітика Монобанк Доллар

        actions.put("chart_Analyse_10_days_MB", chatId -> {
            chart = chartService.buildChartWithNBU(Period.TEN_DAYS, OnlineDollarMonobank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        actions.put("chart_Analyse_month_MB", chatId -> {
            chart = chartService.buildChartWithNBU(Period.MONTH, OnlineDollarMonobank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        actions.put("chart_Analyse_kvartal_MB", chatId -> {
            chart = chartService.buildChartWithNBU(Period.QUARTER, OnlineDollarMonobank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        // Аналітика Монобанк Євро

        actions.put("chart_Analyse_10_days_MB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.TEN_DAYS, OnlineEuroMonoBank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        actions.put("chart_Analyse_month_MB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.MONTH, OnlineEuroMonoBank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        actions.put("chart_Analyse_kvartal_MB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.QUARTER, OnlineEuroMonoBank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);
        });

        // Аналітика Абанк Доллар

        actions.put("chart_Analyse_10_days_AB", chatId -> {
            chart = chartService.buildChartWithNBU(Period.TEN_DAYS, OnlineDollarAbank.class);

           return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        actions.put("chart_Analyse_month_AB", chatId -> {
            chart = chartService.buildChartWithNBU(Period.MONTH, OnlineDollarAbank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        actions.put("chart_Analyse_kvartal", chatId -> {
            chart = chartService.buildChartWithNBU(Period.QUARTER, OnlineDollarAbank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);

        });
        // Аналітика Абанк Євро

        actions.put("chart_Analyse_10_days_AB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.TEN_DAYS, OnlineEuroAbank.class);

            return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        actions.put("chart_Analyse_month_AB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.MONTH, OnlineEuroAbank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);
        });
        actions.put("chart_Analyse_kvartal_AB_€", chatId -> {
            chart = chartService.buildChartWithNBU(Period.QUARTER, OnlineEuroAbank.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);

        });

        //Аналітика стосовно архівного курсу
        actions.put("chart_analyze_main_ER_years", chatId -> {
            System.out.println("ANALIZE ARCHIVE");
            chart = chartService.buildChartWithArchive(Period.MONTH, PrivatBankCurrencyArchive.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);

        });

        actions.put("chart_analyze_main_ER_years_€", chatId -> {
            System.out.println("ANALIZE ARCHIVE");
            chart = chartService.buildChartWithArchive(Period.MONTH, PrivatBankCurrencyArchive.class);
            return telegramMessage.sendChartToTelegram(chart, chatId);

        });
    }

    @Override
    public SendPhoto performAction(String action, long chatId) throws TelegramApiException{
        if (actions.containsKey(action)) {
            return actions.get(action).performPicture(chatId);
        } else {
            // Обработка непредвиденных действий
            throw new TelegramApiException("Непредвиденное действие: " + action);
        }
    }
}