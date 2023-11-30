package com.example.ExchangeRates.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

}
