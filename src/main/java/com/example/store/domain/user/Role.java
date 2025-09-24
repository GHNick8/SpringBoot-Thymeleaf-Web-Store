package com.example.store.domain.user;

import com.example.store.domain.base.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter
@Setter
public class Role extends BaseEntity { 
    private String name; 

}