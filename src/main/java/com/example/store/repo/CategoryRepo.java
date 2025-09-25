package com.example.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store.domain.product.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
