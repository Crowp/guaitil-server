package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ProductType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private Boolean status;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Local local;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Multimedia> multimedia;

    @OneToOne
    private ProductPrice productPrice;
}
