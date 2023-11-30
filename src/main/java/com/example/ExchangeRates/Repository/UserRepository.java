package com.example.ExchangeRates.Repository;

import com.example.ExchangeRates.Entity.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
