package com.apex.wallet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//Spring Boot needs to know how to serialize Java objects into JSON strings
//that is this config ies required so they can be saved cleanly inside Redis's memory space.
@Configuration
public class RedisConfig {
    /*
    Look closely at your new RedisConfig.java class. Spring Boot has a built-in
    bean named redisTemplate. If you define a method named redisTemplate without
     specifying that it should override or replace the default configuration, a configuration collision can happen, throwing a BeanDefinitionStoreException.

    The Fix:
    Open your RedisConfig.java and add a @Primary annotation on top of your bean
    definition, or explicitly name it to ensure it overrides the default bean
    cleanly:
     */

    @Bean(name = "redisTemplate")
    @Primary // Tells Spring to prioritize your custom JSON template over the default one
    public RedisTemplate<String, Object> redisConfig(RedisConnectionFactory connectionFactory){

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(redisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(redisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
