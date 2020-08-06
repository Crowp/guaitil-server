package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Product implements Serializable {

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

    @OneToMany(fetch = FetchType.EAGER)
    private List<Multimedia> multimedia;
}
