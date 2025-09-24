package com.example.store.domain.order;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable 
@Data
public class Address { 
    private String line1, line2, city, zip, country; 
}