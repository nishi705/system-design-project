package com.stockpulse.service;

import com.stockpulse.dto.AlphaVantageRespone;
import com.stockpulse.dto.StockData;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class StockStreamService {
     private final WebClient webClient;
     private final ReactiveKafkaProducerTemplate<String, StockData> producerTemplate;
    private final String API_TOKEN = "NS3NI7KGF7FDTWH4";
    private final String TOPIC_NAME = "stock-prices";


     public StockStreamService(WebClient webClient, ReactiveKafkaProducerTemplate<String, StockData> producerTemplate){
         this.webClient = webClient;
         this.producerTemplate = producerTemplate;
     }

    @EventListener(ApplicationReadyEvent.class)
    public void startContinuousStream() {
        // Step 1: Create a tick every half-second
        Flux.interval(Duration.ofSeconds(10))

                .flatMapSequential(tick -> fetchStockFromThirdParty("AAPL"))
                .flatMapSequential(this::sendToKafka)
                .subscribe(); // Starts the continuous stream
    }

     public Mono<StockData> fetchStockFromThirdParty(String symbol){

         return webClient.get()
                 .uri(uriBuilder -> uriBuilder
                         .path("/query")
                                 .queryParam("function", "GLOBAL_QUOTE")
                                 .queryParam("symbol", symbol)
                                 .queryParam("apikey", API_TOKEN)
                         .build())
                 .retrieve()
                 .bodyToMono(AlphaVantageRespone.class)
                 .map(response -> new StockData(
                         response.globalQuote().symbol(),
                         response.globalQuote().price()
                 ))
                 .onErrorReturn(new StockData(symbol, 0.0));
     }


     private Mono<Void> sendToKafka(StockData stockData){
         /*
         To ensure your app remains completely non-blocking, we shouldn't use the standard
         KafkaTemplate (which blocks the thread while waiting for a response from the broker)
          */
        // System.out.println("publised message to kafka topic is:" +stockData);

         return producerTemplate.send(TOPIC_NAME, stockData.symbol(), stockData)
                 .doOnSuccess(sendResult -> System.out.println("published to kafka topic [" + TOPIC_NAME + "]:" + sendResult))
                 .doOnError(throwable -> System.out.println("failed to published on kafka topic"+ throwable.getMessage()))
                 .then();
     }
}
