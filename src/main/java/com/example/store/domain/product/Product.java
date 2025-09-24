package com.example.store.domain.product;

import java.math.BigDecimal;

import com.example.store.domain.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter
@Setter
public class Product extends BaseEntity {
    private String name;
    
    @Column(length = 1000) 
    private String description;
    
    private BigDecimal price;
    
    private String imageUrl;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    private Category category;
    
    private boolean onSale = false;

}