package com.example.store.config;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.http.ResponseCookie;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class JwtUtils {
    private final SecretKey key; 
    private final String issuer; 
    private final Duration ttl;

    public JwtUtils(String secret, String issuer, Duration ttl){ 
        this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); 
        this.issuer=issuer; 
        this.ttl=ttl; 
    }

    public String generate(String subject){ 
        var now=Instant.now(); 
        return Jwts.builder()
            .setSubject(subject)
            .setIssuer(issuer)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(ttl)))
            .signWith(key, SignatureAlgorithm.HS256).compact(); 
    }

    public String parse(String token){ 
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject(); 
    }

    public String httpOnlyCookie(String token){ 
        return ResponseCookie.from("JWT", token)
            .httpOnly(true)
            .path("/")
            .maxAge(ttl).sameSite("Lax")
            .build().toString(); 
    }

    public String clearCookie(){ 
        return ResponseCookie.from("JWT", "")
            .httpOnly(true)
            .path("/")
            .maxAge(Duration.ZERO)
            .build().toString(); 
    }

    public String readFromCookie(HttpServletRequest req){ 
        return Arrays.stream(Optional.ofNullable(req.getCookies())
            .orElse(new Cookie[0]))
            .filter(c->c.getName()
            .equals("JWT"))
            .map(Cookie::getValue)
            .findFirst().orElse(null); 
    }
    
}