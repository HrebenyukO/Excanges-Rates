package com.example.ExchangeRates.Service;

import com.example.ExchangeRates.Config.BotConfig;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

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
       // addCommandsForMainMenu();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()  &&   update.getMessage().hasText()){
            log.info("HAS MESSAGE START");
           // hasMessage(update);
        }
        else if(update.hasCallbackQuery()) {
            log.info("CALL BACK QUERY START");
           // hasQuery(update);
        }
    }


    private void sendMessage(long chatID,String textToSend){
        SendMessage message=new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);
     /*  ReplyKeyboardMarkup keyboard = mainMenu();
        message.setReplyMarkup(keyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occured: "+e.getMessage());
        }*/
    }

    //https://emojipedia.org/smileys
    private void startCommandReceived(long chatID, String firstName){
        String answer= EmojiParser.
                parseToUnicode("Hello, " +firstName+ " , nice to meet you! :star_struck:");
        log.info("Replied to user "+firstName);
        sendMessage(chatID,answer);
    }

    private void hasMessage(Update update){
        String messageText=update.getMessage().getText();
        long chatID=update.getMessage().getChatId();
        switch (messageText){
            case "/start":

                startCommandReceived(chatID,update.
                        getMessage().
                        getChat().
                        getFirstName());break;

            default:sendMessage(chatID,"Sorry,command was not recognized ");

        }
    }
}
