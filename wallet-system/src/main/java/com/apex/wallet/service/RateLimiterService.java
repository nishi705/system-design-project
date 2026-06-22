package com.apex.wallet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateLimiterService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final String RATE_LIMIT_PREFIX = "rateLimit";
    private final int MAX_REQUEST_SIZE = 5;
    private final int WINDOW_SIZE_SECONDS = 10;

    public boolean isAllowed(String clientId){
        String key = RATE_LIMIT_PREFIX + clientId;

        //
        Long currentRequest = redisTemplate.opsForValue().increment(key);

        if(currentRequest == null){
            return false;
        }

        if(currentRequest == 1){
            //limit the window size
            redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SIZE_SECONDS));
        }

        if(currentRequest > MAX_REQUEST_SIZE){
            log.warn("RATE LIMIT EXCEEDED: Client {} has sent {} requests", clientId, currentRequest);
            return false;// Request blocked!
        }

        return true;// Request allowed
    }

}
