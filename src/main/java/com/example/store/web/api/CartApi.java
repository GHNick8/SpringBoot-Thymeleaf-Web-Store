package com.example.store.web.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.store.service.CartService;
import com.example.store.service.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/cart") 
@RequiredArgsConstructor
public class CartApi { 
    private final CartService cart; 
    private final ProductService products; 
    
    @PostMapping("/add/{id}") 
    public Map<String,Object> add(@PathVariable Long id, @RequestParam(defaultValue="1") int qty, HttpSession s){ 
        cart.add(s, products.get(id), qty); 
        return Map.of("ok",true,"total", cart.total(s)); 
    }

}