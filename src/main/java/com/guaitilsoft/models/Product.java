package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Column
    private Boolean status;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="local_id")
    private Local local;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Multimedia> multimedia;

    @OneToOne
    @JoinColumn(name="product_priceid")
    private ProductPrice productPrice;
}
