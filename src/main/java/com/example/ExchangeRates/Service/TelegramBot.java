package com.example.ExchangeRates.Service;

import com.example.ExchangeRates.Actions.ActionHandler;
import com.example.ExchangeRates.Config.BotConfig;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroAbank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Service.Charts.ChartService;
import com.example.ExchangeRates.Entity.Period;
import com.example.ExchangeRates.Service.ButtonService.ButtonService;
import com.example.ExchangeRates.Service.Currency.CurrencyFacade;

import com.example.ExchangeRates.Service.SendingService.SendMessageService;
import com.example.ExchangeRates.Service.SendingService.TelegramMessage;
import com.example.ExchangeRates.Service.UserService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private ButtonService buttonService;
    @Autowired
    TelegramMessage telegramMessage;
    @Autowired
    private ActionHandler actionHandler;


    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("HAS MESSAGE START");
            hasMessage(update);
        } else if (update.hasCallbackQuery()) {
            log.info("CALL BACK QUERY START");
            hasQuery(update);
        }
    }

    private void hasMessage(Update update) {
        String messageText = update.getMessage().getText();
        long chatID = update.getMessage().getChatId();

        switch (messageText) {
            case "/start":
                userService.registredUser(update.getMessage());
                SendMessage messageHello=telegramMessage.
                        startCommandReceived(chatID, update.getMessage().getChat().getFirstName());
                executeMessage(messageHello);
                break;
            case "💵 КУРСИ ВАЛЮТ":
                SendPhoto sendPhoto= telegramMessage.sendTableToTelegram(chatID);
                executeMessage(sendPhoto);
                break;
            case "📊 Аналітика курсів валют":
                InlineKeyboardMarkup keyboardMarkupChart = buttonService.analyseExchangeRates();
                SendMessage messageAnalyze= telegramMessage.sendMessageWithKeyboard
                        (chatID, "Оберіть один з варіантів", keyboardMarkupChart);
                executeMessage(messageAnalyze);
                break;
            default:
                telegramMessage.sendMessage(chatID, "Sorry,command was not recognized ");

        }
    }

    private void hasQuery(Update update) {
        String data = update.getCallbackQuery().getData();
        long chatID = update.getCallbackQuery().getMessage().getChatId();
        try {
            BotApiMethod<?> botApiMethod = actionHandler.performAction(data, chatID);

            if (botApiMethod != null) {
                execute(botApiMethod);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace(); // Или обработайте исключение соответствующим образом
        }
    }

    void executeMessage(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (
                TelegramApiException e) {
            e.printStackTrace();
        }
    }
    void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (
                TelegramApiException e) {
            e.printStackTrace();

        }
    }
}