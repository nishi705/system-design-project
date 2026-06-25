package com.authorization.authorization.controller;


import com.authorization.authorization.dto.LoginRequest;
import com.authorization.authorization.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/*
We implemented JWT-based authorization. After successful login, Auth Service generates a
JWT containing user roles (ADMIN, USER). Every request carries the JWT in the Authorization header.
A custom JWT filter validates the token and extracts roles. Spring Security then performs API-level
authorization using requestMatchers() and hasRole()/hasAnyRole(). For example, /admin/** is accessible
only by ADMIN users, while /user/** can be accessed by USER and ADMIN roles. The application is stateless and uses SecurityFilterChain with JWT authentication.
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){

        if(loginRequest.getUsername().equals("admin") && loginRequest.getPassword().equals("admin123")){
            return jwtUtil.generateToken(loginRequest.getUsername(), "ADMIN");
        }

        if(loginRequest.getUsername().equals("user") && loginRequest.getPassword().equals("user-123")){
            return jwtUtil.generateToken(loginRequest.getUsername(), "USER");
        }

        throw new RuntimeException("role not found");
    }


}
