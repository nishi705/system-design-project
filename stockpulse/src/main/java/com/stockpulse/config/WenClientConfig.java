package com.stockpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WenClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder
                .baseUrl("https://www.alphavantage.co")
                .build();
    }


}
