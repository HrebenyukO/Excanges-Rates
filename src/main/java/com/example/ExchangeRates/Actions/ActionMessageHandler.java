package com.example.ExchangeRates.Actions;


import com.example.ExchangeRates.Service.ButtonService.ButtonService;
import com.example.ExchangeRates.Service.Charts.ChartService;
import com.example.ExchangeRates.Service.SendingService.TelegramMessage;
import com.example.ExchangeRates.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActionMessageHandler implements ActionHandler<SendMessage>  {
    @Autowired
    private ButtonService buttonService;
    @Autowired
    TelegramMessage telegramMessage;

    @Autowired
    ChartService chartService;
    @Autowired
    private UserService userService;

    private final Map<String, ActionMessage> actions = new HashMap<>();

    public ActionMessageHandler() {
        actions.put("💵 КУРСИ ВАЛЮТ", chatId -> {
            InlineKeyboardMarkup keyboardMarkup = buttonService.menuER();
            return telegramMessage.sendMessageWithKeyboard(
                    chatId,
                    "Банковський курс валют",
                    keyboardMarkup);
        });
        actions.put("📊 Аналітика курсів валют", chatId -> {
            InlineKeyboardMarkup main_menu_Analyze = buttonService.menuAnalyze();
            return telegramMessage.sendMessageWithKeyboard(
                    chatId,
                    "\uD83E\uDD14 Оберіть один із варіантів \uD83E\uDD14",
                    main_menu_Analyze);

        });

        actions.put("\uD83D\uDECE Повідомлення", chatId -> {
            InlineKeyboardMarkup keyBoardNotification = buttonService.mainNotification();
            return telegramMessage.sendMessageWithKeyboard(
                    chatId,
                    "\uD83C\uDF1F Оберіть один з варіантів \uD83C\uDF1F",
                    keyBoardNotification);

        });

        actions.put("Оповіщення про зміни курсу", chatId -> {
            InlineKeyboardMarkup keyboardMarkup = buttonService.notifiсationChainER();
            return telegramMessage.sendMessageWithKeyboard(
                    chatId,
                    "Оповіщення про зміни курсу валют",
                    keyboardMarkup);
        });

        actions.put("start_notification", chatId -> {
            telegramMessage.sendMessage(
                    chatId,
                    "Оповіщення включено повідомлення буде надходити о 9:00");
            userService.turnOfNotification(chatId, true);
            return null;
        });

        actions.put("stop_notification", chatId -> {
            telegramMessage.sendMessage(chatId, "Оповіщення виключено");
            userService.turnOfNotification(chatId, false);
            return null;
        });

        actions.put("start_notification_chain", chatId -> {
            userService.turnOfNotificationChain(chatId, true);
            return telegramMessage.sendMessage(
                    chatId,
                    "✅ Оповіщення про зміни курсу валют включено ✅");
        });

        actions.put("stop_notification_chain", chatId -> {
            userService.turnOfNotificationChain(chatId, false);
            return telegramMessage.sendMessage(
                    chatId,
                    "❌ Оповіщення про зміни курсу валют виключено ❌");
        });

        actions.put("notification_main_change_ER",chatId -> {
            InlineKeyboardMarkup keyboardChangeER = buttonService.notifiсationChainER();
            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "\uD83D\uDCC8 Ви будете отримувати інформацію при зміні курсу валют \uD83D\uDCC9",
                    keyboardChangeER);
        });
        actions.put("notification_main_time_ER",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.notifiсation();

            return telegramMessage.sendMessage(chatId,
                    "⏰ Ви будете отримувати інформацію про курс валют у встановлений час ⏰"
                   );
        });

        actions.put("notification_main_time_ER",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.notifiсation();

            return telegramMessage.sendMessage(chatId,
                    "⏰ Ви будете отримувати інформацію про курс валют у встановлений час ⏰"
            );
        });

        actions.put("analyze_main_ER_NBU",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.analyzeBanksDollar();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть один з варіантів",keyboardTimeER
            );
        });

        actions.put("analyze_main_ER_NBU_€",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.analyzeBanksEuro();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть один з варіантів",keyboardTimeER
            );
        });

        actions.put("analyze_main_dollar_menu",chatId -> {
            InlineKeyboardMarkup keyboardDollarAnalyze = buttonService.mainAnalyzeDollar();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть банк один з варіантів",keyboardDollarAnalyze
            );
        });
        actions.put("analyze_main_EURO_menu",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.mainAnalyzeEuro();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть банк",keyboardTimeER
            );
        });

        actions.put("PrivatChart_$",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.privatbankAnalyseDollar();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть період",keyboardTimeER
            );
        });
        actions.put("PrivatChart_€",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.privatbankAnalyseEuro();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть період",keyboardTimeER
            );
        });

        actions.put("MonobankChart_$",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.monoBankAnalyseDollar();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть період",keyboardTimeER
            );
        });
        actions.put("MonobankChart_€",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.monoBankAnalyseEuro();
            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть період",keyboardTimeER
            );
        });

        actions.put("AbankChart_$",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.aBankAnalyseDollar();
            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть період",keyboardTimeER
            );
        });

        actions.put("PrivatChart_€",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.privatbankAnalyseEuro();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть період",keyboardTimeER
            );
        });
        actions.put("MonobankChart_€",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.monoBankAnalyseEuro();

            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть період",keyboardTimeER
            );
        });
        actions.put("AbankChart_€",chatId -> {
            InlineKeyboardMarkup keyboardTimeER = buttonService.aBankAnalyseEuro();
            return telegramMessage.sendMessageWithKeyboard(chatId,
                    "Оберіть період",keyboardTimeER
            );
        });


    }

    @Override
    public SendMessage performAction(String action, long chatId) throws TelegramApiException {
        if (actions.containsKey(action)) {
            return actions.get(action).perform(chatId);
        } else {
            // Обработка непредвиденных действий
            throw new TelegramApiException("Непредвиденное действие: " + action);
        }
    }
}

