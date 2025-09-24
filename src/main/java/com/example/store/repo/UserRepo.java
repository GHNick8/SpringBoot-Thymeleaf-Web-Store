package com.example.store.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store.domain.user.User;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
}