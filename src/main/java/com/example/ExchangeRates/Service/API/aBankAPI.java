package com.example.ExchangeRates.Service.API;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class aBankAPI {
    private final WebClient webClient;

    public aBankAPI(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://minfin.com.ua").build();
    }

    public String getCurrencyPage() {
        return webClient.get()
                .uri("/company/a-bank/currency/")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
