package com.example.ExchangeRates.Service;

import com.example.ExchangeRates.Config.BotConfig;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private ExchangeRates exchangeRates;
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
    public TelegramBot(BotConfig botConfig){
        this.botConfig=botConfig;
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

    private void hasMessage(Update update){
        String messageText=update.getMessage().getText();
        long chatID=update.getMessage().getChatId();
        switch (messageText){
            case "/start":
                userService.registredUser(update.getMessage());
                startCommandReceived(chatID,update.getMessage().getChat().getFirstName());
                break;
            case "💵 КУРСИ ВАЛЮТ":
                InlineKeyboardMarkup keyboardMarkup=exchangeRates.menuExchangeRates();
                sendMessage(chatID,"$$$$$$$$--КУРСИ ВАЛЮТ--$$$$$$$$$",keyboardMarkup);
                break;
            default:sendMessage(chatID,"Sorry,command was not recognized ");

        }
    }

    private void hasQuery(Update update){
        String data=update.getCallbackQuery().getData();
        long chatID=update.getCallbackQuery().getMessage().getChatId();
        switch (data){
            case "Оповіщення по курсу валют":
                InlineKeyboardMarkup keyboardMarkup=exchangeRates.notifivation();
                sendMessage(chatID, "Оповіщення про курс валют", keyboardMarkup);
                break;
            case "start_notification":  sendMessage(chatID,"Оповіщення включено");
                break;
            case "stop_notification":
                sendMessage(chatID,"Оповіщення виключено");
                break;
        }
    }
    private void sendMessage(long chatID,String textToSend){
        SendMessage message=new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);
        ReplyKeyboardMarkup keyboard = mainMenu();
        message.setReplyMarkup(keyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occured: "+e.getMessage());
        }
    }

    private void sendMessage(long chatID,String textToSend,InlineKeyboardMarkup keyboardMarkup){
        SendMessage message=new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occured: "+e.getMessage());
        }
    }





    private static ReplyKeyboardMarkup mainMenu() {
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

    //https://emojipedia.org/smileys
    private void startCommandReceived(long chatID, String firstName){
        String answer= EmojiParser.
                parseToUnicode("Hello, " +firstName+ " , " +
                        "nice to meet you! :star_struck:");
        log.info("Replied to user "+firstName);
        sendMessage(chatID,answer);
    }

}
