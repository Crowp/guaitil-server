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
    @Column(name = "food_id")
    private Long id;

    @Column(nullable = false,name = "food_type")
    private  String foodType;

    @OneToOne(fetch = FetchType.EAGER)
    private Kitchen kitchen;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

}
