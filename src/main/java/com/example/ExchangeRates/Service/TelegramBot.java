package com.example.ExchangeRates.Service;


import com.example.ExchangeRates.Actions.ActionHandler;
import com.example.ExchangeRates.Actions.ActionMessageHandler;
import com.example.ExchangeRates.Actions.ActionPictureHandler;
import com.example.ExchangeRates.Config.BotConfig;
import com.example.ExchangeRates.Mappers.EntityMapper.ArchiveCurrencyMapper;
import com.example.ExchangeRates.Service.ButtonService.ButtonService;

import com.example.ExchangeRates.Service.SendingService.TelegramMessage;
import com.example.ExchangeRates.Service.UserService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
    TelegramMessage telegramMessage;
    @Autowired
    private ActionHandler actionMessageHandler;
    @Autowired
    ActionPictureHandler actionPictureHandler;


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
            hasMessage(update, actionMessageHandler);
        } else if (update.hasCallbackQuery()) {
            log.info("CALL BACK QUERY START");
            hasQuery(update, actionMessageHandler);
        }
    }

    private void hasMessage(Update update, ActionHandler<?> actionHandler) {
        String messageText = update.getMessage().getText();
        long chatID = update.getMessage().getChatId();
        if(messageText.equals("/start")){
            userService.registredUser(update.getMessage());
            SendMessage messageHello = telegramMessage.startCommandReceived(chatID, update.getMessage().getChat().getFirstName());
            executeMessage(messageHello);
        }
        else {
        try {
            Object botApiMethod = actionHandler.performAction(messageText, chatID);
            executeMessage(botApiMethod);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }}
    }
    private void hasQuery(Update update, ActionHandler<?> actionHandler) {
        String data = update.getCallbackQuery().getData();
        long chatID = update.getCallbackQuery().getMessage().getChatId();
        Object botApiMethod=null;
        try {
            if(data.startsWith("chart")){
            botApiMethod=actionPictureHandler.performAction(data,chatID);
            }
            else {
                botApiMethod = actionHandler.performAction(data, chatID);
            }
            executeMessage(botApiMethod);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    void executeMessage(Object message) {
        try {
            if (message instanceof SendMessage) {
                execute((SendMessage) message);
            } else if (message instanceof SendPhoto) {
                execute((SendPhoto) message);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}