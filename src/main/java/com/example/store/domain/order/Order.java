package com.example.store.domain.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.store.domain.base.BaseEntity;
import com.example.store.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne 
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = new ArrayList<>();
    
    @Embedded 
    private Address shippingAddress;
    
    private BigDecimal total;
    private String status; 
    
}