//package com.authorization.authorization;
//
//import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
//import com.github.tomakehurst.wiremock.junit5.WireMockTest;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
//
//@SpringBootTest
//@WireMockTest
//public class OktaServiceIntegrationTest {
//
//    @DynamicPropertySource
//    static void configureProperty(DynamicPropertyRegistry register, WireMockRuntimeInfo wiremockInfo){
//             register.add("okta.api.base-uri", wiremockInfo::getHttpBaseUrl);
//    }
//
//    @Test
//    void verifyWireMockIsRunning(){
//        stubFor(get(urlEqualTo("/test-endpoint"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withBody("Wiremock is working!")));
//    }
//}
