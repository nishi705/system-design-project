//package com.urlshortening.controller;
//
//import com.urlshortening.model.User;
//import com.urlshortening.repository.RegisterUserRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/spi/user")
//public class RegisterController {
//    private final RegisterUserRepository repository;
//
//    public RegisterController(RegisterUserRepository repository){
//        this.repository = repository;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody User user){
//        repository.save(user);
//        return ResponseEntity.ok("user registered successfully");
//
//    }
//}
