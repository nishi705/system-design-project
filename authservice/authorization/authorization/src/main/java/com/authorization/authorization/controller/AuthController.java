package com.authorization.authorization.controller;


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
    public String login(@RequestBody String username,
                        @RequestBody String password){

        if(username.equals("admin") && password.equals("admin123")){
            return jwtUtil.generateToken(username, "ADMIN");
        }

        if(username.equals("user") && password.equals("user-123")){
            return jwtUtil.generateToken(username, "USER");
        }

        throw new RuntimeException("role not found");
    }


}
