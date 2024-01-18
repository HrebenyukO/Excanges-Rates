package com.example.ExchangeRates.Service.UserService;

import com.example.ExchangeRates.Entity.Currency.User.Notification;
import com.example.ExchangeRates.Entity.Currency.User.User;
import com.example.ExchangeRates.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;


@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void registredUser(Message message) {
        if (userRepository.findById(message.getChatId()).isEmpty()) {
            var chat = message.getChat();
            var chatID = message.getChatId();
            User user = new User();
            user.setChat_id(chatID);
            user.setUserName(chat.getUserName());
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            Notification notification=new Notification(true,true);
            notification.setUser(user);
            user.setNotification(notification);
            user.setRegistered_at(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            log.info("User is created "+user.getUserName());
        }
    }
    @Transactional
    public void turnOfNotification(Long ID,boolean value){
        User user=userRepository.findById(ID).orElseThrow();
        Notification notification=user.getNotification();
        notification.setNotification_status(value);
    }

    @Transactional
    public void turnOfNotificationChain(Long ID,boolean value){
        User user=userRepository.findById(ID).orElseThrow();
        Notification notification=user.getNotification();
        notification.setNotification_about_chain_ER(value);
    }
}