package com.example.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store.domain.order.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {}