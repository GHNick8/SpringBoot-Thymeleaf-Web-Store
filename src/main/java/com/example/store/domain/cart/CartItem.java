package com.example.store.domain.cart;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItem { 
    private Long productId; 
    private String name; 
    private BigDecimal price; 
    private int qty; 

}