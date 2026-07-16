//package com.authorization.authorization.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClient;
//
//@Service
//public class OktaService {
//
//    private final RestClient restClient;
//
//    public OktaService(@Value("${okta.api.base-uri}") String baseUrl, RestClient.Builder builder){
//        this.restClient = builder.baseUrl(baseUrl).build();
//    }
//
//    public String getOpenIdConfiguration(){
//        return this.restClient.get()
//                .uri("/.well-known/openid-configuration")
//                .retrieve()
//                .body(String.class);
//    }
//}
