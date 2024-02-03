package com.example.ExchangeRates.Service.API;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Component
public class ukrGazBankAPI {
    private final WebClient webClient;

    public ukrGazBankAPI(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://minfin.com.ua").build();
    }

    public String getCurrencyPage() {
        return webClient.get()
                .uri("/company/ukrgasbank/currency/")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
