package com.authorization.authorization.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

//    @GetMapping("/profile")
//    public String profile() {
//        return "User Profile";
//    }

//    @GetMapping("/profile")
//    public String profile(Authentication authentication) {
//
//        return "Hello " + authentication.getName();
//
//    }


    //For the Actual claim
    @GetMapping("/profile")
    public Map<String, Object> profile(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();

    }

    //Or we can return the specific claim

//    @GetMapping("/profile")
//    public String profile(@AuthenticationPrincipal Jwt jwt) {
//
//        return "Hello " + jwt.getClaimAsString("name");
//
//    }

    //Best when wwe want jwt authentication

//    @GetMapping("/profile")
//    public String profile(Authentication authentication) {
//
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//
//        String email = jwt.getClaimAsString("email");
//
//        return "Hello " + email;
//    }

}
