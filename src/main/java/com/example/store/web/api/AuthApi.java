package com.example.store.web.api;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store.config.JwtUtils;
import com.example.store.dto.LoginDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final AuthenticationManager auth;
    private final JwtUtils jwt;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        auth.authenticate(token); // throws if invalid

        var jwtToken = jwt.generate(dto.email());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwt.httpOnlyCookie(jwtToken))
                .body(Map.of("ok", true));
    }
}
