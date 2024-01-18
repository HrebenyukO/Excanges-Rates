package com.example.ExchangeRates.Entity.Currency.User;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
public class User{

    @Id
    private long chat_id;
    private String firstName;
    private String lastName;
    private String userName;
    private Timestamp registered_at;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Notification notification;

}
