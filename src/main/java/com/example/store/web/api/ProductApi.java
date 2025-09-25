package com.example.store.web.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.store.domain.product.Product;
import com.example.store.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/products") 
@RequiredArgsConstructor
public class ProductApi { 
    private final ProductService service; 

    @GetMapping 
    public Page<Product> list(@RequestParam(required=false) String q, Pageable p){ 
        return service.search(q,p);
    } 
    
    @GetMapping("/{id}") 
    public Product one(@PathVariable Long id){ 
        return service.get(id);
    } 
    
}