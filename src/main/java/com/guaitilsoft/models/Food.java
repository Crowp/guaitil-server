package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Food implements Serializable {

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
