package com.urlshortening.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "short_url", indexes = { @Index(name = "idx_code_unique", columnList = "code", unique = true)})
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 32)
    private String code;

    @Column(name = "target_url", unique = true, length = 2048)
    private String targetUrl;


    private Instant expiresAt;
    private int hits;
    private Instant lastAccessedAt;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private Instant createAt = Instant.now();

}
