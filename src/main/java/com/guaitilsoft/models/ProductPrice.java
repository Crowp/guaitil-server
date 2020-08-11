package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductPrice implements Serializable {

    @Id
    @Column(name = "price_id")
    private Long id;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private Long sale;
}
