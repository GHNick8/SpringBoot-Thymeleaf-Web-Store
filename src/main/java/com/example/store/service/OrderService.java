package com.example.store.service;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.store.domain.cart.CartItem;
import com.example.store.domain.order.Address;
import com.example.store.domain.order.Order;
import com.example.store.domain.order.OrderItem;
import com.example.store.domain.product.Product;
import com.example.store.domain.user.User;
import com.example.store.repo.OrderRepo;
import com.example.store.repo.ProductRepo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final CartService cartService;

    public Order placeOrder(User customer, Address shipping, HttpSession session) {
        var cart = cartService.getCart(session).values();

        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty, cannot place order.");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setShippingAddress(shipping);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem ci : cart) {
            Product product = productRepo.findById(ci.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + ci.getProductId()));

            OrderItem oi = new OrderItem();
            oi.setProduct(product);
            oi.setQuantity(ci.getQty());
            oi.setUnitPrice(product.getPrice());

            order.getItems().add(oi);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(ci.getQty())));
        }

        order.setTotal(total);
        order.setStatus("NEW");

        // Save to DB
        Order saved = orderRepo.save(order);

        // Clear cart
        session.removeAttribute("cart");

        return saved;
    }

    public List<Order> findOrdersForUser(User user) {
        return orderRepo.findAll()
                .stream()
                .filter(o -> o.getCustomer().getId().equals(user.getId()))
                .toList();
    }
}
