package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Local local;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Multimedia> multimedia;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductPrice productPrice;
}
