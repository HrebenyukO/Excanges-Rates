package com.example.ExchangeRates.Service.API;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MonoBankAPI {
    public static final String MONO_BANK_API_URL_ExchangeRate =
            "https://api.monobank.ua/bank/currency";
    private final WebClient webClient;

    public MonoBankAPI() {
        this.webClient = WebClient.create();
    }

    public Mono<String> getApiMonoBank() {
        return webClient.get()
                .uri(MONO_BANK_API_URL_ExchangeRate)
                .retrieve()
                .bodyToMono(String.class);
    }
}
