package com.stockpulse.config;

import com.stockpulse.dto.StockData;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, StockData> reactiveKafkaProducerTemplate(KafkaProperties properties){

        //take default properties
        Map<String, Object> props = properties.buildProducerProperties(null);

        SenderOptions<String, StockData> senderOptions = SenderOptions.<String,StockData>create(props)
                .withKeySerializer(new StringSerializer())
                .withValueSerializer(new JsonSerializer<StockData>());

        return new ReactiveKafkaProducerTemplate<String, StockData>(senderOptions);




    }

}
