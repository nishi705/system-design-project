package com.authorization.authorization.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtUtil {
    private final String accessToken = "mysecretkeymysecretkeymysecretkeymysecretkey";

    public String generateToken(String username, String role){

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(
                        Keys.hmacShaKeyFor(
                                accessToken.getBytes()
                        ),
                        SignatureAlgorithm.HS256
                )
                .compact();

    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                accessToken.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
