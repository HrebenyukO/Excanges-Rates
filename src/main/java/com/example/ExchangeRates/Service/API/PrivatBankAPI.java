package com.example.ExchangeRates.Service.API;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PrivatBankAPI {
    private static final String PRIVAT_BANK_API_URL_GOTIVKA =
            "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private static final String PRIVAT_BANK_API_URL_BEZGOTIVKA =
            "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";

    private final WebClient webClient;

    public PrivatBankAPI() {
        this.webClient = WebClient.create();
    }

    public Mono<String> getApiPrivatBankOnline() {
        return webClient.get()
                .uri(PRIVAT_BANK_API_URL_BEZGOTIVKA)
                .retrieve()
                .bodyToMono(String.class);
    }
    public Mono<String> getApiPrivatBankGotivka() {
        return webClient.get()
                .uri(PRIVAT_BANK_API_URL_GOTIVKA)
                .retrieve()
                .bodyToMono(String.class);
    }
}
