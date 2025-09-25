package com.example.store.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.store.domain.user.User;
import com.example.store.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo users;
    private final PasswordEncoder encoder; 

    @Override
    public UserDetails loadUserByUsername(String email) {
        return users.findByEmail(email).orElseThrow();
    }

    public User register(String email, String name, String rawPwd) {
        if (users.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        var u = new User();
        u.setEmail(email);
        u.setFullName(name);
        u.setPassword(encoder.encode(rawPwd));
        return users.save(u);
    }
}
