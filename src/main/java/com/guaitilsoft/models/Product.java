package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Product {

    @Id
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "cost_price")
    private Float costPrice;

    @Column(nullable = false, name = "sale_price")
    private Float salePrice;
}
