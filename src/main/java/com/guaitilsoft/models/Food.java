package com.guaitilsoft.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Food {

    @Id
    @Column
    private Long id;

    @Column(nullable = false,name = "food_type")
    private  String foodType;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Kitchen> kitchens;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products;

}
