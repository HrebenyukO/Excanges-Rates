package com.example.ExchangeRates.Service.SendingService;

import com.example.ExchangeRates.Service.ButtonService.ButtonService;
import com.example.ExchangeRates.Service.Currency.CurrencyFacade;
import com.example.ExchangeRates.Service.TelegramBot;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;

@Service
@Slf4j
public class SendMessageService implements TelegramMessage {

    byte[] imageBytes;
    @Autowired
    ButtonService buttonService;
    @Autowired
    CurrencyFacade currencyFacade;

    @Override
    public SendMessage sendMessage(long chatID, String textToSend){
        SendMessage message=new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);
        ReplyKeyboardMarkup keyboard = buttonService.mainMenu();
        message.setReplyMarkup(keyboard);
      return message;
    }
    public SendMessage sendMessageWithKeyboard(long chatID, String textToSend, InlineKeyboardMarkup keyboardMarkup){
        SendMessage message=new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);
        message.setReplyMarkup(keyboardMarkup);
       return message;
    }

    @Override
    public SendPhoto sendTableToTelegram(long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        imageBytes = currencyFacade.createTable();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageBytes), "chart.png"));
      return sendPhoto;
    }
    //https://emojipedia.org/smileys
    @Override
    public SendMessage startCommandReceived(long chatID, String firstName){
        String answer= EmojiParser.
                parseToUnicode("Hello, " +firstName+ " , " +
                        "nice to meet you! :star_struck:");
        log.info("Replied to user "+firstName);
        return sendMessage(chatID,answer);
    }
    @Override
   public SendPhoto sendChartToTelegram(byte [] array, long chatID){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatID);
        sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(array), "chart.png"));
        return sendPhoto;
    }
}
