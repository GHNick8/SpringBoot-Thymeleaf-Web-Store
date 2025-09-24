package com.example.store.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store.domain.product.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String q, Pageable pageable);
}
