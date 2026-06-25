package com.authorization.authorization.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtil {
    private final String SECRET = "mysecretkeymysecretkeymysecretkeymysecretkey";

    public String generateToken(String username, String role){

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(//getting signing key here
                        Keys.hmacShaKeyFor(//creates a cryptographic key object.
                                SECRET.getBytes()
                        ),
                        SignatureAlgorithm.HS256//HS256 is one of the most common JWT signing algorithms.
                )
                .compact();

    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
