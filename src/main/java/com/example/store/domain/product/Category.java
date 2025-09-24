package com.example.store.domain.product;

import com.example.store.domain.base.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter
@Setter
public class Category extends BaseEntity { 
    private String name; 

}