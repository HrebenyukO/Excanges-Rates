package com.example.ExchangeRates.Service.API;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SensBankApi {
    private final WebClient webClient;

    public SensBankApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://sensebank.ua").build();

    }

    public String getCurrencyPage() {
        return webClient.get()
                .uri("/currency-exchange/")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
