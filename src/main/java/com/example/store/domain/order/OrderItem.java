package com.example.store.domain.order;

import java.math.BigDecimal;

import com.example.store.domain.base.BaseEntity;
import com.example.store.domain.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter
public class OrderItem extends BaseEntity {
    @ManyToOne 
    private Product product;
    
    private int quantity;
    private BigDecimal unitPrice;
    
}