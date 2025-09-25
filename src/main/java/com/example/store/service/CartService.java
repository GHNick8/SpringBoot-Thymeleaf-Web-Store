package com.example.store.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.store.domain.cart.CartItem;
import com.example.store.domain.product.Product;
import com.example.store.repo.ProductRepo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepo productRepo;

    @SuppressWarnings("unchecked")
    public Map<Long, CartItem> getCart(HttpSession session) {
        var map = (Map<Long, CartItem>) session.getAttribute("cart");
        if (map == null) {
            map = new LinkedHashMap<>();
            session.setAttribute("cart", map);
        }
        return map;
    }

    // For Thymeleaf controller (adds 1 by default)
    public void addProduct(HttpSession session, Long id) {
        var product = productRepo.findById(id).orElseThrow();
        add(session, product, 1);
    }

    // For API (adds any qty)
    public void add(HttpSession session, Product product, int qty) {
        Map<Long, CartItem> cart = getCart(session);
        cart.compute(product.getId(), (k, existing) -> {
            if (existing == null) {
                var item = new CartItem();
                item.setProductId(product.getId());
                item.setName(product.getName());
                item.setPrice(product.getPrice());
                item.setQty(qty);
                return item;
            } else {
                existing.setQty(existing.getQty() + qty);
                return existing;
            }
        });
    }

    // Calculate total
    public BigDecimal total(HttpSession session) {
        return getCart(session).values().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
