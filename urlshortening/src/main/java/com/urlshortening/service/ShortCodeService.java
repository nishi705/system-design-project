package com.urlshortening.service;

import com.urlshortening.Base62;
import com.urlshortening.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortCodeService {
    private static final int DEFAULT_LENGTH = 7;
    private final ShortUrlRepository repository;

    private final Base62 base62;

    public ShortCodeService(Base62 base62,ShortUrlRepository repository){
        this.base62 = base62;
        this.repository = repository;
    }

    public String generateUniqueCode(){
        for(int i=0;i<10;i++){
            String candidate = Base62.randomCode(DEFAULT_LENGTH);
            if (!repository.existsByCode(candidate)){
                return candidate;
            }
        }
        throw  new IllegalStateException("Cannot generate the unique code");

    }

    public String validateCustomAlias(String alias){
        if(alias == null || alias.isBlank())return null;
        if(alias.matches("^[a-zA-Z0-9_]{3,32}$")){
            throw new IllegalArgumentException("Alias must be 3–32 characters long and contain only letters, digits, or underscores");
        }
        if(repository.existsByCode(alias)){
            throw new IllegalArgumentException("alias already exist");
        }
        return alias;

    }
}
