package com.example.ExchangeRates.Actions;


import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Entity.Period;
import com.example.ExchangeRates.Service.ButtonService.ButtonService;
import com.example.ExchangeRates.Service.Charts.ChartService;
import com.example.ExchangeRates.Service.SendingService.TelegramMessage;
import com.example.ExchangeRates.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActionHandler{
    @Autowired
    private ButtonService buttonService;
    @Autowired
    TelegramMessage telegramMessage;

    @Autowired
    ChartService chartService;
    @Autowired
    private UserService userService;
     private byte[] euroOnline = null;
    private byte[] dollarOnline = null;

    private final Map<String, Action> actions = new HashMap<>();

    public ActionHandler() {
        actions.put("Оповіщення по курсу валют", chatId -> {
            InlineKeyboardMarkup keyboardMarkup = buttonService.notifiсation();
            return telegramMessage.sendMessageWithKeyboard(
                    chatId, "Оповіщення про курс валют", keyboardMarkup);

        });

        actions.put("Оповіщення про зміни курсу", chatId -> {
            InlineKeyboardMarkup keyboardMarkup = buttonService.notifiсation2();
            return telegramMessage.sendMessageWithKeyboard(chatId, "Оповіщення про зміни курсу валют", keyboardMarkup);

        });

        actions.put("start_notification", chatId -> {
            telegramMessage.sendMessage(chatId, "Оповіщення включено повідомлення буде надходити о 9:00");
            userService.turnOfNotification(chatId, true);
            return null;
        });

        actions.put("stop_notification", chatId -> {
            telegramMessage.sendMessage(chatId, "Оповіщення виключено");
            userService.turnOfNotification(chatId, false);
            return null;
        });

        actions.put("start_notification_chain", chatId -> {
            telegramMessage.sendMessage(chatId, "Оповіщення про зміни курсу валют включено");
            userService.turnOfNotificationChain(chatId, true);
            return null;
        });

        actions.put("stop_notification_chain", chatId -> {
            telegramMessage.sendMessage(chatId, "Оповіщення про зміни курсу валют виключено");
            userService.turnOfNotificationChain(chatId, false);
            return null;
        });

      /*  actions.put("PrivatChart", chatId -> {
            InlineKeyboardMarkup keyboardMarkup = buttonService.privatbankAnalyse();
            String message = "Курс валют у ПриватБанку";
            telegramMessage.sendMessageWithKeyboard(chatId, message, keyboardMarkup);

            euroOnline = chartService.buildChart(Period.TEN_DAYS, OnlineEuroPrivatBank.class);
            dollarOnline = chartService.buildChart(Period.TEN_DAYS, OnlineDollarPrivatBank.class);

            telegramMessage.sendChartToTelegram(euroOnline, chatId);
            return telegramMessage.sendChartToTelegram(dollarOnline, chatId);

        });*/

          /*   actions.put("Analyse_month_PB", (PhotoAction) chatId -> {
             euroOnline = chartService.buildChart(Period.MONTH, OnlineEuroPrivatBank.class);
             dollarOnline = chartService.buildChart(Period.MONTH, OnlineDollarPrivatBank.class);

                telegramMessage.sendChartToTelegram(euroOnline, chatId);
                 telegramMessage.sendChartToTelegram(dollarOnline, chatId);
            return new SendPhoto(chatId, new InputFile(euroOnline));
        });*/
            /*
        actions.put("MonobankChart", chatId -> {
            InlineKeyboardMarkup keyboardMarkup = buttonService.monoBankAnalyse();
            euroOnline = chartService.buildChart(Period.TEN_DAYS, OnlineEuroMonoBank.class);
            dollarOnline = chartService.buildChart(Period.TEN_DAYS, OnlineDollarMonobank.class);

            telegramMessage.sendMessageWithKeyboard(chatId, "Курс валют за 10 днів у МоноБанк", keyboardMarkup);
            telegramMessage.sendChartToTelegram(euroOnline, chatId);
            telegramMessage.sendChartToTelegram(dollarOnline, chatId);
            return new SendPhoto(chatId, new InputFile(euroOnline)); // возвращает SendPhoto
        });

        actions.put("Analyse_month_MB", chatId -> {
            euroOnline = chartService.buildChart(Period.MONTH, OnlineEuroMonoBank.class);
            dollarOnline = chartService.buildChart(Period.MONTH, OnlineDollarMonobank.class);

            telegramMessage.sendChartToTelegram(euroOnline, chatId);
            telegramMessage.sendChartToTelegram(dollarOnline, chatId);
            return new SendPhoto(chatId, new InputFile(euroOnline)); // возвращает SendPhoto
        });

        actions.put("AbankChart", chatId -> {
            InlineKeyboardMarkup keyboardMarkup = buttonService.aBankAnalyse();
            telegramMessage.sendMessageWithKeyboard(chatId, "Курс валют за 10 днів у АБанк", keyboardMarkup);

            euroOnline = chartService.buildChart(Period.TEN_DAYS, OnlineEuroAbank.class);
            dollarOnline = chartService.buildChart(Period.TEN_DAYS, OnlineDollarAbank.class);

            telegramMessage.sendChartToTelegram(euroOnline, chatId);
            telegramMessage.sendChartToTelegram(dollarOnline, chatId);
            return new SendPhoto(chatId, new InputFile(euroOnline)); // возвращает SendPhoto
        });*/

        // Добавьте остальные действия аналогичным образом
    }
    public BotApiMethod<?> performAction(String action, long chatId) throws TelegramApiException {
        if (actions.containsKey(action)) {
            return actions.get(action).perform(chatId);
        } else {
            // Обработка непредвиденных действий
            throw new TelegramApiException("Непредвиденное действие: " + action);
        }
    }
}

