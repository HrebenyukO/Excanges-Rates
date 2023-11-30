package com.example.ExchangeRates.Service;

import com.example.ExchangeRates.Entity.User;
import com.example.ExchangeRates.Repository.UserRepository;
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
            user.setRegistered_at(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            log.info("User is created "+user.getUserName());
        }
    }


}