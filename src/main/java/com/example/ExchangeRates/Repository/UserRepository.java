package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.Currency.User.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
