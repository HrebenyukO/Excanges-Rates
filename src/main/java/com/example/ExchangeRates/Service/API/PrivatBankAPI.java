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

    private static final String PRIVAT_BANK_ARCHIVE_URL=
            "https://api.privatbank.ua/p24api/exchange_rates?json&date=01.12.2014";
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

    public Mono<String> getApiPrivatBankArchive(String date) {
        // Добавляем параметр даты к URL
        String apiUrlWithDate = PRIVAT_BANK_ARCHIVE_URL + "&date=" + date;

        return webClient.get()
                .uri(apiUrlWithDate)
                .retrieve()
                .bodyToMono(String.class);
    }
}
