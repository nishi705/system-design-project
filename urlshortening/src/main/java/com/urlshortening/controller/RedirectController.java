package com.urlshortening.controller;

import com.urlshortening.model.ShortRequestUrlDTO;
import com.urlshortening.model.ShortUrl;
import com.urlshortening.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService service){
        this.urlService = service;

    }

    @PostMapping("/createShort")
    public ResponseEntity<ShortUrl> create(@Valid @RequestBody ShortRequestUrlDTO requestUrl){
        ShortUrl result = urlService.create(requestUrl);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code){
                          //Object

        return urlService.lookupActive(code)
                .map(url -> {

                    // Analytics
                    urlService.registerHit(url);

                    return ResponseEntity.status(HttpStatus.FOUND)
                            .header(HttpHeaders.LOCATION, url.getTargetUrl())
                            .body(url.getTargetUrl());
                            //.<Void>build();

                })
                .orElse(ResponseEntity.notFound().build());

//        return urlService.lookupActive(code)
//                .map(url -> {
//                    urlService.registerHit(url);
//
//                    return ResponseEntity.status(302)
//                            .header(HttpHeaders.LOCATION, url.getTargetUrl())
//                            .build();
//                })
//                .orElseGet(() -> {
//                        return ResponseEntity.notFound().build();
//                });

    }
}
