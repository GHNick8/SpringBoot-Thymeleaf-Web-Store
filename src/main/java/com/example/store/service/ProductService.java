package com.example.store.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.store.domain.product.Product;
import com.example.store.repo.ProductRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo repo;

    public Page<Product> search(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return repo.findAll(pageable);
        }
        return repo.findByNameContainingIgnoreCase(q, pageable);
    }

    public Product get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public List<Product> findAll() {
        return repo.findAll();
    }
}
