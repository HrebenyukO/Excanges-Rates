package com.example.ExchangeRates.Service;

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
import com.example.ExchangeRates.Service.UserService.UserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private ButtonService buttonService;
    @Autowired
    ChartService chartService;

    @Autowired
    CurrencyFacade currencyFacade;
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
                InlineKeyboardMarkup keyboardMarkup= buttonService.menuExchangeRates();

                byte [] tableCurrency=currencyFacade.createTable();

               sendChartToTelegram(tableCurrency,chatID);
                break;
            case "📊 Аналітика курсів валют":
                InlineKeyboardMarkup keyboardMarkupChart= buttonService.analyseExchangeRates();
                sendMessage(chatID,"Оберіть один з варіантів",keyboardMarkupChart);
               break;
            default:sendMessage(chatID,"Sorry,command was not recognized ");

        }
    }

    private void hasQuery(Update update){
        String data=update.getCallbackQuery().getData();
        long chatID=update.getCallbackQuery().getMessage().getChatId();
        byte [] euroOnline=null;
        byte [] dollarOnline=null;
        switch (data){
            case "Оповіщення по курсу валют":
                InlineKeyboardMarkup keyboardMarkup1= buttonService.notifiсation();
                sendMessage(chatID, "Оповіщення про курс валют", keyboardMarkup1);
                break;
            case "Оповіщення про зміни курсу":
                InlineKeyboardMarkup keyboardMarkup2=buttonService.notifiсation2();
                sendMessage(chatID,"Оповіщення про зміни курсу валют",keyboardMarkup2);
                break;
            case "start_notification":
                sendMessage(chatID,"Оповіщення включено повідомлення буде надходити о 9:00");
                userService.turnOfNotification(chatID,true);
                break;
            case "stop_notification":
                sendMessage(chatID,"Оповіщення виключено");
                userService.turnOfNotification(chatID,false);
                break;
            case "start_notification_chain":
                sendMessage(chatID,"Оповіщення про зміни курсу валют включено");
              userService.turnOfNotificationChain(chatID,true);
                break;
            case "stop_notification_chain":
                sendMessage(chatID,"Оповіщення про зміни курсу валют виключено");
                userService.turnOfNotificationChain(chatID,false);
                break;
            case "PrivatChart":
                InlineKeyboardMarkup keyboardMarkup3=buttonService.privatbankAnalyse();
                var message="Курс валют у ПриватБанку ";
                sendMessage(chatID,message,keyboardMarkup3);

                euroOnline=chartService.buildChart(
                        Period.TEN_DAYS,
                        OnlineEuroPrivatBank.class);
               dollarOnline=chartService.buildChart(
                        Period.TEN_DAYS,
                        OnlineDollarPrivatBank.class);
                sendChartToTelegram(euroOnline,chatID);
                sendChartToTelegram(dollarOnline,chatID);
                break;
            case "Analyse_month_PB":
                //sendMessage(chatID,"Курс валют останній місяць ");
                euroOnline=chartService.buildChart(
                        Period.MONTH,
                        OnlineEuroPrivatBank.class);
                dollarOnline=chartService.buildChart(
                        Period.MONTH,
                        OnlineDollarPrivatBank.class);
                sendChartToTelegram(euroOnline,chatID);
                sendChartToTelegram(dollarOnline,chatID);
                break;
            case "MonobankChart":
                InlineKeyboardMarkup keyboardMarkup4=buttonService.monoBankAnalyse();
               // sendMessage(chatID,"Курс валют за 10 днів у МоноБанк",keyboardMarkup4);
                euroOnline=chartService.buildChart(
                        Period.TEN_DAYS,
                        OnlineEuroMonoBank.class);
                dollarOnline=chartService.buildChart(
                        Period.TEN_DAYS,
                        OnlineDollarMonobank.class);
                sendChartToTelegram(euroOnline,chatID);
                sendChartToTelegram(dollarOnline,chatID);
                break;
            case "Analyse_month_MB":
                //sendMessage(chatID,"Курс валют останній місяць у МоноБанк");
                euroOnline=chartService.buildChart(
                        Period.MONTH,
                        OnlineEuroMonoBank.class);
                dollarOnline=chartService.buildChart(
                        Period.MONTH,
                        OnlineDollarMonobank.class);
                sendChartToTelegram(euroOnline,chatID);
                sendChartToTelegram(dollarOnline,chatID);
                break;
            case "AbankChart":
                InlineKeyboardMarkup keyboardMarkup5=buttonService.aBankAnalyse();
                sendMessage(chatID,"Курс валют за 10 днів у АБанк",keyboardMarkup5);
                euroOnline=chartService.buildChart(
                        Period.TEN_DAYS,
                        OnlineEuroAbank.class);
                dollarOnline=chartService.buildChart(
                        Period.TEN_DAYS,
                        OnlineDollarAbank.class);
                sendChartToTelegram(euroOnline,chatID);
                sendChartToTelegram(dollarOnline,chatID);
                break;
        }

    }
    public void sendMessage(long chatID, String textToSend){
        SendMessage message=new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);
        ReplyKeyboardMarkup keyboard = buttonService.mainMenu();
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

    public void sendChartToTelegram(byte[] imageBytes, long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageBytes), "chart.png"));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
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
