package com.urlshortening.service;

import com.urlshortening.model.ShortRequestUrlDTO;
import com.urlshortening.model.ShortUrl;
import com.urlshortening.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;

@Service
public class UrlService {
    private final ShortCodeService service;
    private final ShortUrlRepository repository;

    public UrlService(ShortCodeService service, ShortUrlRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    public ShortUrl create(ShortRequestUrlDTO request) {
        String target = normalizeUrl(request.getUrl());
        String code = request.getAlias() != null
                ? service.validateCustomAlias(request.getAlias())
                : service.generateUniqueCode();
        Instant expiresAt = null;

        ShortUrl shortUrl = ShortUrl.builder()
                .code(code)
                .targetUrl(target)
                .expiresAt(expiresAt)
                .build();

        return repository.save(shortUrl);

    }

    public Optional<ShortUrl> lookupActive(String code) {
        return repository.findByCode(code)
                .filter(url -> url.getExpiresAt() == null || url.getExpiresAt().isAfter(Instant.now()));

    }

    public void registerHit(ShortUrl url) {
        url.setHits(url.getHits() + 1);
        url.setLastAccessedAt(Instant.now());
        repository.save(url);
    }

    public String normalizeUrl(String url) {
        try {
            URI uri = new URI(url.trim());
            if (uri.getScheme() == null) {
                uri = new URI("http://" + url.trim());
            }
            if (!uri.getScheme().equalsIgnoreCase("http") && !uri.getScheme().equalsIgnoreCase("https")) {
                throw new IllegalArgumentException("Only HTTP and HTTPS are supported");
            }
            return uri.normalize().toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL");
        }
    }
}
