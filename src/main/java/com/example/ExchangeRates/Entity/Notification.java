package com.example.ExchangeRates.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "user")
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "value")
    private boolean notification_status;
    @Column
    private boolean notification_about_chain_ER;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Notification (Boolean notification_status,Boolean notification_about_chain_ER){
        this.notification_status =notification_status;
        this.notification_about_chain_ER=notification_about_chain_ER;
    }
}
