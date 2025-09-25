package com.example.store.web.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.store.service.CartService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // View cart
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        return "cart";
    }

    // Add product by ID
    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        cartService.addProduct(session, id);
        return "redirect:/cart"; // always redirect after POST
    }

    // Clear cart
    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        cartService.getCart(session).clear();
        return "redirect:/cart";
    }
}
